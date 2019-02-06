package com.lckiss.pagecenter.presenter

import com.lckiss.baselib.common.compatResult
import com.lckiss.baselib.presenter.BasePresenter
import com.lckiss.baselib.rx.observer.ProgressObserver
import com.lckiss.pagecenter.data.model.AppInfoModel
import com.lckiss.pagecenter.presenter.contarct.AppInfoContract
import com.lckiss.provider.data.protocol.AppInfo
import javax.inject.Inject

/**
 * 详情页面业务逻辑
 */
class AppDetailPresenter @Inject constructor(model: AppInfoModel, view: AppInfoContract.AppDetailView) : BasePresenter<AppInfoModel, AppInfoContract.AppDetailView>(model, view) {

    var mPerModel:AppInfoModel = model

    var mPerView:AppInfoContract.AppDetailView = view

    /**
     * 获取app详情
     */
    fun getAppDetail(id: Int) {
        mPerModel.getAppDetail(id).compatResult()
                .subscribe(object : ProgressObserver<AppInfo>(mContext, mPerView) {
                    override fun onNext(appInfo: AppInfo) {
                        mPerView.showAppDetail(appInfo)
                    }
                })
    }

}