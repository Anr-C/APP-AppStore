package com.lckiss.appstore.ui.fragment

import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.widget.Toast
import com.lckiss.appstore.R
import com.lckiss.baselib.common.BaseConstant.Companion.APK_DOWNLOAD_DIR
import com.lckiss.baselib.common.execute
import com.lckiss.baselib.utils.ACache
import com.lckiss.baselib.utils.AppUtils
import com.lckiss.baselib.utils.DataCleanManager
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Consumer

/**
 * 设置界面
 */
class SettingFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)

        //设置版本号
        val keyAboutMe = preferenceManager.findPreference(getString(R.string.key_about_me))
        keyAboutMe.summary = "版本号:" + AppUtils.getAppVersionName(activity, activity.packageName)

        //显示下载位置
        val mApkDownloadDirPref = preferenceManager.findPreference(getString(R.string.key_apk_download_dir))
        mApkDownloadDirPref.summary = ACache.get(activity).getAsString(APK_DOWNLOAD_DIR)
        //选择下载位置
        mApkDownloadDirPref.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            Toast.makeText(activity, "价值不大,暂不实现", Toast.LENGTH_SHORT).show()
            false
        }

        //清理缓存
        val mClearCachePref = preferenceManager.findPreference(getString(R.string.key_clear_cache))
        try {
            mClearCachePref.setSummary(DataCleanManager.getCacheSize(activity.cacheDir))

            mClearCachePref.onPreferenceClickListener = Preference.OnPreferenceClickListener {

                Observable.create(ObservableOnSubscribe<Int> { e ->
                    DataCleanManager.cleanFiles(activity)
                    DataCleanManager.cleanInternalCache(activity)
                    DataCleanManager.deleteFolderFile(ACache.get(activity).getAsString(APK_DOWNLOAD_DIR) + ".tmp", false)
                    e.onNext(1)
                    e.onComplete()
                }).execute().subscribe {
                    Toast.makeText(this@SettingFragment.activity, "清理成功", Toast.LENGTH_LONG).show()
                    mClearCachePref.summary = DataCleanManager.getCacheSize(activity.cacheDir)
                }
                true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}