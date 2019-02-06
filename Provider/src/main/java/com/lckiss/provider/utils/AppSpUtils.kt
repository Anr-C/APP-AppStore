package com.lckiss.provider.utils

import android.content.Context
import com.lckiss.provider.R

/**
 * 获取SharedPreferences值
 */
fun getSettingKey(context: Context, key: Int, defaultValue: Boolean): Boolean {
    val sharedPreferences = context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(context.resources.getString(key), defaultValue)
}

/**
 * 修改SharedPreferences值
 */
fun putSettingKey(context: Context, key: Int, value: Boolean) {
    val sharedPreferences = context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
    return sharedPreferences.edit().putBoolean(context.resources.getString(key), value).apply()
}
