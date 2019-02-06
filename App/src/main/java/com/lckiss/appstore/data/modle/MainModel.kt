package com.lckiss.appstore.data.modle

import com.lckiss.appstore.data.api.MainApi
import com.lckiss.appstore.presenter.contract.MainContract
import com.lckiss.provider.data.protocol.AppInfo
import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.provider.data.protocol.AppsUpdateReq
import io.reactivex.Observable

/**
 * MainModel定义
 */
class MainModel(private val mUpdateApiService: MainApi) : MainContract.IMainModel {
    /**
     * 获取需要更新的应用列表
     */
    override fun getUpdateApps(param: AppsUpdateReq): Observable<BaseReq<List<AppInfo>>> {
        return mUpdateApiService.getAppsUpdateinfo(param.packageName, param.versionCode)
    }
}