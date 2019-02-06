package com.lckiss.baselib.common

import android.app.Application
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.lckiss.baselib.common.BaseConstant.Companion.APK_DOWNLOAD_DIR
import com.lckiss.baselib.common.font.CustomFont
import com.lckiss.baselib.data.net.DownloadSqlLiteActor
import com.lckiss.baselib.injection.component.AppComponent
import com.lckiss.baselib.injection.component.DaggerAppComponent
import com.lckiss.baselib.injection.module.AppModule
import com.lckiss.baselib.utils.ACache
import com.mikepenz.iconics.Iconics
import zlc.season.rxdownload3.core.DownloadConfig
import zlc.season.rxdownload3.extension.ApkInstallExtension

/**
 * BaseApplication定义
 */
class BaseApplication : Application() {

    //用于缓存的View
    lateinit var cacheView: View

    lateinit var appComponent: AppComponent;

    override fun onCreate() {
        super.onCreate()

        //Custom Fonts
        Iconics.init(applicationContext)
        Iconics.registerFont(CustomFont())

        //ARouter初始化
        ARouter.openLog()    // 打印日志
        ARouter.openDebug()
        ARouter.init(this)

        initRxDownload()

        initAppInjection()

    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    private fun initRxDownload() {
        val path = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).path
        ACache.get(this).put(APK_DOWNLOAD_DIR, path)

        val builder = DownloadConfig.Builder.create(this)
                //设置更新频率
                .setFps(20)
                .setDbActor(DownloadSqlLiteActor(this))
                //自动开始下载
                .enableAutoStart(false)
                //设置默认的下载位置
                .setDefaultPath(path)
                //启用数据库
                .enableDb(true)
                //启用Service
                .enableService(true)
                .setMaxRange(10)
                .setMaxMission(10)
                .setDebug(true)
                .addExtension(ApkInstallExtension::class.java)
        DownloadConfig.init(builder)
    }

}