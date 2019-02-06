package com.lckiss.appstore.presenter.contract

import com.lckiss.provider.data.protocol.AppInfo
import com.lckiss.provider.data.protocol.AppsUpdateReq
import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.baselib.presenter.view.BaseView
import io.reactivex.Observable

/**
 * Main模块抽象层定义
 */
interface MainContract {

    interface MainView : BaseView {
        fun requestPermissionSuccess()
        fun requestPermissionFail()
        fun changeAppNeedUpdateCount(count: Int)
    }

    /**
     * 获取更新信息
     */
    interface IMainModel {
        fun getUpdateApps(param: AppsUpdateReq): Observable<BaseReq<List<AppInfo>>>
    }
}