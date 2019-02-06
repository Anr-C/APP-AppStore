package com.lckiss.provider.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.lckiss.provider.data.protocol.Apk
import java.io.File
import java.util.*

/**
 * 获取所有已经安装的apk
 */
fun getInstalledApps(context: Context): List<Apk> {

    val pm = context.packageManager

    val packageInfos: List<PackageInfo>
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        packageInfos = pm.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES)
    } else {
        packageInfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES)
    }
    val installedApp = ArrayList<Apk>(packageInfos.size)
    packageInfos.map {
        val apk = Apk()
        apk.packageName=it.packageName
        apk.appVersionCode=it.versionCode.toString()
        apk.appVersionName=it.versionName
        apk.lastUpdateTime=it.lastUpdateTime
        val applicationInfo = it.applicationInfo
        if (applicationInfo != null) {
            apk.apkPath=applicationInfo.sourceDir
            apk.appName=applicationInfo.loadLabel(pm).toString()
            apk.mDrawable=applicationInfo.loadIcon(pm)
            apk.isSystem=applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM > 0
        }
        installedApp.add(apk)
    }
    installedApp.sort()

    return installedApp
}

/**
 * 扫描指定目录中的Apk
 */
fun scanApks(context: Context,dir: String): List<Apk> {
    val file = File(dir)
    if (!file.isDirectory) {
        throw RuntimeException("is not Dir")
    }
    val apks = file.listFiles { f ->
        if (f.isDirectory) {
            false
        } else f.name.endsWith(".apk")
    }

    val androidApks = ArrayList<Apk>()
    for (apk in apks!!) {
        val androidApk = Apk.read(context, apk.path)
        if (androidApk != null) {
            androidApks.add(androidApk)
        }
    }
    return androidApks
}


/**
 * 获取指定目录中未安装的APK
 */
fun getUninstallAPK(context: Context, path: String): Apk? {
    val pm = context.packageManager
    val info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES)
    if (info != null) {
        val apk = Apk()
        apk.packageName = info.packageName
        apk.apkPath = path
        apk.appVersionCode = info.versionCode.toString()
        apk.appVersionName = info.versionName
        val res: Resources = getResources(context, path)
        var labelRes: String
        var icon: Drawable?
        try {
            labelRes = res.getString(info.applicationInfo.labelRes)
        } catch (e: Exception) {
            //                e.printStackTrace();
            labelRes = info.applicationInfo.loadLabel(pm).toString()
        }
        try {
            icon = res.getDrawable(info.applicationInfo.icon)
        } catch (e: Exception) {
            //                e.printStackTrace();
            icon = info.applicationInfo.loadIcon(pm)
        }

        apk.appName = labelRes
        apk.mDrawable = icon
        return apk
    }
    return null
}

/**
 * 获取资源文件
 */
fun getResources(context: Context, apkPath: String): Resources {

    // 反射得到assetMagCls对象并实例化,有参数
    val assetMagCls = Class.forName("android.content.res.AssetManager")
    val assetMag = assetMagCls.newInstance()
    var typeArgs = arrayOfNulls<Class<*>>(1)
    typeArgs[0] = String::class.java
    val assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod("addAssetPath",
            *typeArgs)
    var valueArgs = arrayOfNulls<Any>(1)
    valueArgs[0] = apkPath
    assetMag_addAssetPathMtd.invoke(assetMag, *valueArgs)
    var res = context.resources
    typeArgs = arrayOfNulls(3)
    typeArgs[0] = assetMag.javaClass
    typeArgs[1] = res.displayMetrics.javaClass
    typeArgs[2] = res.configuration.javaClass
    val resCt = Resources::class.java.getConstructor(*typeArgs)
    valueArgs = arrayOfNulls(3)
    valueArgs[0] = assetMag
    valueArgs[1] = res.displayMetrics
    valueArgs[2] = res.configuration
    res = resCt.newInstance(*valueArgs) as Resources
    return res
}

