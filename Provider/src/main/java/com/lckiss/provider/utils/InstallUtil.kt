package com.lckiss.provider.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.widget.Toast
import com.lckiss.baselib.utils.AppUtils
import com.lckiss.baselib.utils.PackageUtils
import com.lckiss.baselib.utils.ShellUtils
import com.lckiss.provider.R
import com.lckiss.baselib.data.protocol.DownloadMission
import com.lckiss.provider.data.protocol.Apk
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.extension.ApkInstallExtension
import java.util.*

/**
 * 一个用于管理安装方式 与卸载方式的工具
 */
fun chooseInstallType(context: Context, apkPath: String, mission: DownloadMission?) {
    val mSmartInstall = getSettingKey(context, R.string.key_smart_install, false)
    val mRootInstall = getSettingKey(context, R.string.key_root_install, false)
    //ROOT安装
    if (mRootInstall) {
        if (ShellUtils.checkRootPermission()) {
            PackageUtils.install(context, apkPath)
        } else {
            putSettingKey(context, R.string.key_root_install, false)
            Toast.makeText(context, "您的手机暂未ROOT,ROOT安装失败,已自动关闭!", Toast.LENGTH_SHORT).show()
            AppUtils.installApk(context, apkPath)
        }
    } else if (mSmartInstall) {
        //辅助服务安装
        PackageUtils.installNormal(context, apkPath)
    } else {
        //使用RxDownload的安装
        if (mission != null) {
            RxDownload.extension(mission, ApkInstallExtension::class.java).subscribe()
        } else {
            AppUtils.installApk(context, apkPath)
        }
    }
}

/**
 * 选择卸载类型
 */
fun chooseUnInstallType(context: Context, apk: Apk) {
    val keyRootInstall = getSettingKey(context, R.string.key_root_install, false)
    if (keyRootInstall) {
        if (ShellUtils.checkRootPermission()) {
            PackageUtils.uninstall(context, apk.packageName)
        } else {
            putSettingKey(context, R.string.key_root_install, false)
            AppUtils.uninstallApk(context, apk.packageName)
            Toast.makeText(context, "您的手机暂未ROOT,ROOT安装失败,已自动关闭!", Toast.LENGTH_SHORT).show()
        }
    } else {
        AppUtils.uninstallApk(context, apk.packageName)
    }
}