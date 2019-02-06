package com.lckiss.appcenter.data.model

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.lckiss.provider.data.protocol.Apk
import com.lckiss.appcenter.presenter.contract.AppManagerContract
import com.lckiss.baselib.common.BaseConstant.Companion.APK_DOWNLOAD_DIR
import com.lckiss.baselib.utils.ACache
import com.lckiss.provider.utils.getInstalledApps
import com.lckiss.provider.utils.scanApks
import io.reactivex.Observable
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.core.Mission
import java.io.File
import java.util.*

/**
 * AppManagerModel
 */

class AppManagerModel(private val context: Context) : AppManagerContract.IAppManagerModel {

    override val downloadMission: Observable<List<Mission>>
        get() = RxDownload.getAllMission().toObservable()

    override val localApks: Observable<List<Apk>>
        get() {
            val dir = ACache.get(context).getAsString(APK_DOWNLOAD_DIR)
            return Observable.create<List<Apk>> { e ->
                e.onNext(scanApks(context,dir))
                e.onComplete()
            }
        }

    override val installedApps: Observable<List<Apk>>
        get() = Observable.create<List<Apk>> {
            it.onNext(getInstalledApps(context))
            it.onComplete()
        }

}
