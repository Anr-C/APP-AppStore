package com.lckiss.provider.data.protocol

import android.content.Context
import android.graphics.drawable.Drawable
import com.lckiss.provider.utils.getUninstallAPK

/**
 * APK属性定义
 */
class Apk : Comparable<Apk> {

    companion object {
        fun read(context: Context, path: String): Apk? {
            val apk = getUninstallAPK(context, path)
            if (apk != null) {
                apk.apkPath = path
            }
            return apk
        }
    }

    var appName: String = ""
    var appVersionName: String = ""
    var appVersionCode: String = ""
    var packageName: String = ""
    var minSdkVersion: String = ""
    var targetSdkVersion: String = ""
    var mDrawable: Drawable? = null
    var isSystem: Boolean = false // 是否是系统自带的App

    var apkPath: String = ""

    var lastUpdateTime: Long = 0

    override fun compareTo(other: Apk): Int {
        return if (other.isSystem) -1 else 1
    }

}