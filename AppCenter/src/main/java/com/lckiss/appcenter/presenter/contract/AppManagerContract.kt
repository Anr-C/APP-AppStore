package com.lckiss.appcenter.presenter.contract

import com.lckiss.provider.data.protocol.Apk
import com.lckiss.baselib.data.protocol.DownloadMission
import com.lckiss.baselib.presenter.view.BaseView
import com.lckiss.provider.data.protocol.AppInfo
import io.reactivex.Observable
import zlc.season.rxdownload3.core.Mission

/**
 * 应用管理的抽象层
 */
interface AppManagerContract {

    interface IAppManagerView : BaseView {
        fun showDownload(downloadMissions: List<DownloadMission>)

        fun showApps(apps: List<Apk>)

        fun showUpdateApps(appInfos: List<AppInfo>)
    }

    interface IAppManagerModel {

        val downloadMission: Observable<List<Mission>>

        val localApks: Observable<List<Apk>>

        val installedApps: Observable<List<Apk>>
    }

}