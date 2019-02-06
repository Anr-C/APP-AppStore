package com.lckiss.pagecenter.presenter

import com.lckiss.baselib.common.compatResult
import com.lckiss.baselib.presenter.BasePresenter
import com.lckiss.baselib.rx.observer.ProgressObserver
import com.lckiss.pagecenter.data.model.AppInfoModel
import com.lckiss.pagecenter.data.protocol.Index
import com.lckiss.pagecenter.presenter.contarct.AppInfoContract
import javax.inject.Inject

/**
 * 推荐页面 业务逻辑
 */
class RecommendPresenter : BasePresenter<AppInfoModel, AppInfoContract.View> {

    @Inject
    constructor(mModel: AppInfoModel, mView: AppInfoContract.View) : super(mModel, mView)

    fun requestDatas() {
        mModel!!.index().compatResult()
                .subscribe(object : ProgressObserver<Index>(mContext, mView) {
                    override fun onNext(t: Index) {
                        (mView as AppInfoContract.View).showResult(t)
                    }
                })
    }

}