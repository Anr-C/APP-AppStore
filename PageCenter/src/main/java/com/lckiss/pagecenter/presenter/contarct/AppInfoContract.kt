package com.lckiss.pagecenter.presenter.contarct

import com.lckiss.baselib.presenter.view.BaseView
import com.lckiss.pagecenter.data.protocol.Index
import com.lckiss.pagecenter.data.protocol.Page
import com.lckiss.provider.data.protocol.AppInfo

/**
 * 首页抽象层
 */
interface AppInfoContract {

    interface View : BaseView {
        fun showResult(index: Index)
        fun onRequestPermissionSuccess()
        fun onRequestPermissionError()
    }

    interface AppInfoView : BaseView {
        fun showResult(page: Page<AppInfo>)
        fun onLoadMoreComplete()
    }

    interface AppDetailView : BaseView {
        fun showAppDetail(appInfo: AppInfo)
    }
}