package com.lckiss.appcenter.presenter

import android.content.IntentFilter
import android.os.Handler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lckiss.provider.data.protocol.Apk
import com.lckiss.baselib.data.protocol.DownloadMission
import com.lckiss.appcenter.presenter.contract.AppManagerContract
import com.lckiss.appcenter.receiver.AppInstallReceiver
import com.lckiss.baselib.common.BaseConstant.Companion.APP_UPDATE_LIST
import com.lckiss.baselib.common.execute
import com.lckiss.baselib.presenter.BasePresenter
import com.lckiss.baselib.rx.observer.ProgressObserver
import com.lckiss.baselib.utils.ACache
import com.lckiss.provider.data.protocol.AppInfo
import io.reactivex.Observable
import zlc.season.rxdownload3.core.Mission
import java.util.ArrayList
import javax.inject.Inject

/**
 * APP管理业务逻辑
 */
class AppManagerPresenter : BasePresenter<AppManagerContract.IAppManagerModel, AppManagerContract.IAppManagerView> {

    private var mPerModel: AppManagerContract.IAppManagerModel
    private var mPerView: AppManagerContract.IAppManagerView

    private lateinit var mAppInstallReceiver:AppInstallReceiver

    @Inject
    constructor(mModel: AppManagerContract.IAppManagerModel, mView: AppManagerContract.IAppManagerView) : super(mModel, mView) {
        mPerModel = mModel
        mPerView = mView
    }

    /**
     * 获取下载中的任务
     */
    fun getDownloadingApps() {
        mPerModel.downloadMission.execute()
                .subscribe(object : ProgressObserver<List<Mission>>(mContext, mPerView) {
                    override fun onNext(missions: List<Mission>) {
                        val downloadingMission = ArrayList<DownloadMission>()
                        for (m in missions) {
                            val status = (m as DownloadMission).status
                            //TODO status不一定准确
                            if (status in 1..3) {
                                downloadingMission.add(m)
                            }
                        }
                        mPerView.showDownload(downloadingMission)
                    }
                })

    }

    /**
     * 获取已下载任务
     */
    fun getDownloadedApps() {
        mPerModel.downloadMission.execute()
                .subscribe(object : ProgressObserver<List<Mission>>(mContext, mPerView) {
                    override fun onNext(missions: List<Mission>) {
                        val downloadedMissions = ArrayList<DownloadMission>()
                        for (r in missions) {
                            if ((r as DownloadMission).status > 3) {
                                downloadedMissions.add(r)
                            }
                        }
                        mPerView.showDownload(downloadedMissions)
                    }
                })

    }

    /**
     * 获取本地Apk
     */
    fun getLocalApks() {
        mPerModel.localApks.execute()
                .subscribe(object : ProgressObserver<List<Apk>>(mContext, mPerView) {
                    override fun onNext(localApks: List<Apk>) {
                        mPerView.showApps(localApks)
                    }
                })
    }

    /**
     * 获取已经安装的app
     */
    fun getInstalledApps() {
        mPerModel.installedApps.execute()
                .subscribe(object : ProgressObserver<List<Apk>>(mContext, mPerView) {
                    override fun onNext(androidApks: List<Apk>) {
                        mPerView.showApps(androidApks)
                    }
                })
    }

    /**
     * 获取更新的app
     */
    fun getUpdateApps() {
        val json = ACache.get(mContext).getAsString(APP_UPDATE_LIST)
        if (json!=null) {
            val gson = Gson()
            val apps = gson.fromJson<List<AppInfo>>(json, object : TypeToken<List<AppInfo>>() {}.type)
            Observable.just<List<AppInfo>>(apps)
                    .execute()
                    .subscribe(object : ProgressObserver<List<AppInfo>>(mContext, mPerView) {
                        override fun onNext(appInfos: List<AppInfo>) {
                            mPerView.showUpdateApps(appInfos)
                        }
                    })
        }
    }

    /**
     * 注册广播接收器
     */
    fun initReceiver(handler: Handler) {
        mAppInstallReceiver = AppInstallReceiver(handler)
        val filter = IntentFilter()
        filter.addAction("android.intent.action.PACKAGE_ADDED")
        filter.addAction("android.intent.action.PACKAGE_REMOVED")
        filter.addDataScheme("package")
        mContext.registerReceiver(mAppInstallReceiver, filter)
    }

    /**
     * 取消注册广播接收器
     */
    fun destroyReceiver() {
        mContext.unregisterReceiver(mAppInstallReceiver)
    }

    /**
     * 获取位置
     */
    fun getPosition(list: List<Apk>, pkg: String): Int {
        for (i in list.indices) {
            if (list[i].packageName.equals(pkg)) {
                return i
            }
        }
        return -1
    }

}