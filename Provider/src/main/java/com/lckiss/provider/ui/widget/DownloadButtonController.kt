package com.lckiss.provider.ui.widget

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lckiss.baselib.common.BaseConstant.Companion.APK_DOWNLOAD_DIR
import com.lckiss.baselib.common.BaseConstant.Companion.APP_UPDATE_LIST
import com.lckiss.baselib.common.compatResult
import com.lckiss.baselib.common.compatResultWithMethod
import com.lckiss.baselib.common.execute
import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.baselib.utils.ACache
import com.lckiss.baselib.utils.AppUtils
import com.lckiss.baselib.utils.AppUtils.runApp
import com.lckiss.provider.R
import com.lckiss.provider.data.protocol.AppDownloadInfo
import com.lckiss.provider.data.protocol.AppInfo
import com.lckiss.baselib.data.protocol.DownloadMission
import com.lckiss.provider.data.protocol.OtherStatus
import com.lckiss.provider.utils.appInfoToMission
import com.lckiss.provider.utils.chooseInstallType
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.core.*
import zlc.season.rxdownload3.extension.ApkInstallExtension
import java.io.File

/**
 * 处理所有界面下载管理功能
 */
class DownloadButtonController(val mRetrofit: Retrofit) {

    internal interface DownloadApi {
        @GET("download/{id}")
        fun getAppDownloadInfo(@Path("id") id: Int): Observable<BaseReq<AppDownloadInfo>>
    }

    private var mDownloadApi: DownloadApi

    init {
        mDownloadApi = mRetrofit.create(DownloadApi::class.java)
    }

    internal inner class DownloadConsumer(var mBtn: DownloadProgressButton, private var mission: DownloadMission) : Consumer<Status> {

        override fun accept(status: Status) {
            mBtn.setTag(R.id.tag_apk_flag, status)

            mBtn.setOnClickListener {
                val clickStatus = mBtn.getTag(R.id.tag_apk_flag) as Status
                when (clickStatus) {
                    is Normal -> {
                        startDownload(mission, mBtn)
                    }
                    is Suspend -> {
                        startDownload(mission, mBtn)
                    }
                    is Waiting -> {
                        mBtn.setText("耐心等待")
                    }
                    is Downloading -> {
                        RxDownload.stop(mission).subscribe()
                    }
                    is Failed -> {
                        startDownload(mission, mBtn)
                    }
                    is Succeed -> {
                        val apkPath = ACache.get(mBtn.context).getAsString(APK_DOWNLOAD_DIR) + File.separator + "${mission.displayName}_${mission.versionName}.apk"
                        val apk = File(apkPath)
                        if (!apk.exists()) {
                            RxDownload.delete(mission, true).subscribe()
                        }
                        chooseInstallType(mBtn.context, apkPath, mission)
                    }
                    is OtherStatus.Installed, is ApkInstallExtension.Installed -> {
                        runApp(mBtn.context, mission.packageName)
                    }
                    is OtherStatus.NeedUpgrade -> {
                        startDownload(mission, mBtn)
                    }
                    is Deleted -> {
                        startDownload(mission, mBtn)
                    }
                }
            }

            when (status) {
                is Normal -> {
                    //自动下载标志位
                    if ("NONE" != mission.url && mission.needAutoStart == 1) {
                        //创建任务后无论什么情况均不需要自动开始
                        mission.needAutoStart == 0
                        RxDownload.start(mission).subscribe()
                    }
                    mBtn.download()
                }
                is Suspend -> {
                    mBtn.setProgress((status.downloadSize * 100 / status.totalSize).toInt())
                    mBtn.paused()
                }
                is Downloading -> {
                    mBtn.setProgress((status.downloadSize * 100 / status.totalSize).toInt())
                }
                is Waiting -> {
                    mBtn.setText("等待中")
                }
                is Failed -> {
                    mBtn.setText("失败重试")
                }
                is Succeed -> {
                    mBtn.setText("安装")
                }
                is OtherStatus.Installed, is ApkInstallExtension.Installed -> {
                    mBtn.setText("运行")
                }
                is OtherStatus.NeedUpgrade -> {
                    mBtn.setText("升级")
                }
                is Deleted -> {
                    mBtn.setText("重新下载")
                }
            }
        }
    }

    /**
     * 用于下载管理等调用 直接下载
     */
    fun handClick(mBtn: DownloadProgressButton, mission: DownloadMission) {
        RxDownload.create(mission).observeOn(AndroidSchedulers.mainThread()).subscribe(DownloadConsumer(mBtn, mission))
    }

    /**
     *  用于在未创建任务界面调用 需要实时获取状态
     */
    fun handClick(mBtn: DownloadProgressButton, appInfo: AppInfo) {
        isAppInstalled(mBtn.context, appInfo)
                .flatMap { status ->
                    if (status is OtherStatus.UnInstall) {
                        isApkFileExist(mBtn.context, appInfo)
                    } else Observable.just(status)
                }
                .flatMap { status ->
                    if (status is OtherStatus.Exists) {
                        mDownloadApi.getAppDownloadInfo(appInfo.id).execute().compatResultWithMethod(mBtn.context, {
                            Toast.makeText(mBtn.context, it, Toast.LENGTH_SHORT).show()
                        }).flatMap {
                            //保存下载数据
                            appInfo.appDownloadInfo = it
                            //创建下载任务
                            RxDownload.create(appInfoToMission(appInfo))
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .toObservable()
                        }
                    } else {
                        //不存在 则返回原来的状态，没安装 也没文件 就是普通待下载
                        Observable.just(status)
                    }
                }
                .execute()
                .subscribe(DownloadConsumer(mBtn, appInfoToMission(appInfo)))
    }

    /**
     * 判断应该是否已安装 返回 安装 未安装 待升级 三种状态
     */
    private fun isAppInstalled(context: Context, appInfo: AppInfo): Observable<Status> {
        val gson = Gson()
        val json = ACache.get(context).getAsString(APP_UPDATE_LIST)
        val apps = gson.fromJson<List<AppInfo>>(json, object : TypeToken<List<AppInfo>>() {}.type)

        var status = if (AppUtils.isInstalled(context, appInfo.packageName))
            OtherStatus.Installed()
        else
            OtherStatus.UnInstall()
        //没有需要更新的APP列表 返回安装或者未安装状态即可
        if (apps == null) {
            return Observable.just(status)
        }

        //否则遍历升级列表 需要升级则返回待升级状态
        if (status is OtherStatus.Installed) {
            apps.map {
                if (it.packageName == appInfo.packageName) {
                    status = OtherStatus.NeedUpgrade()
                }
            }
        }
        return Observable.just(status)
    }

    /**
     * 判断APK文件是否存在 返回 存在 和Normal普通待下载 两种状态
     */
    private fun isApkFileExist(context: Context, appInfo: AppInfo): Observable<Status> {
        val file = File(ACache.get(context).getAsString(APK_DOWNLOAD_DIR) + File.separator + "${appInfo.displayName}_${appInfo.versionName}.apk.download")
        val status = if (file.exists()) OtherStatus.Exists() else Normal(Status(0, 0, true))
        return Observable.just(status)
    }

    /**
     * 开始下载 判断两种状态 1.已经获取下载信息 2.未获取下载信息
     */
    private fun startDownload(mission: DownloadMission, mBtn: DownloadProgressButton) {
        if (mission.url.isEmpty()) {
            //因为没有下载记录 所以链接为空，重新从服务器获取
            mDownloadApi.getAppDownloadInfo(mission.id).execute().compatResultWithMethod(mBtn.context, {
                Toast.makeText(mBtn.context, it, Toast.LENGTH_SHORT).show()
            }).flatMap {
                mission.url = it.downloadUrl
                //创建任务并需要自动开始下载 设置值1 创建任务后无论什么情况均不需要自动开始
                mission.needAutoStart = 1
                RxDownload.create(mission)
                        .observeOn(AndroidSchedulers.mainThread())
                        .toObservable()
            }.subscribe(DownloadConsumer(mBtn, mission))
        } else {
            RxDownload.start(mission).subscribe()
        }
    }


}