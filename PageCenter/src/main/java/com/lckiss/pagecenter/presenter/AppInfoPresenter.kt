package com.lckiss.pagecenter.presenter

import com.lckiss.baselib.common.compatResult
import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.baselib.presenter.BasePresenter
import com.lckiss.baselib.rx.observer.ErrorHandlerObserver
import com.lckiss.baselib.rx.observer.ProgressObserver
import com.lckiss.pagecenter.data.model.AppInfoModel
import com.lckiss.pagecenter.data.protocol.Page
import com.lckiss.pagecenter.presenter.contarct.AppInfoContract
import com.lckiss.provider.data.protocol.AppInfo
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * 为所有的App列表做逻辑处理
 */
class AppInfoPresenter : BasePresenter<AppInfoModel, AppInfoContract.AppInfoView> {

    var mPerView: AppInfoContract.AppInfoView
    var mPerModel: AppInfoModel

    companion object {
        const val TOP_LIST = 1
        const val GAME = 2
        const val HOT_APP_LIST = 3
    }

    @Inject
    constructor(mModel: AppInfoModel, mView: AppInfoContract.AppInfoView) : super(mModel, mView) {
        mPerView = mView
        mPerModel = mModel
    }

    /**
     * 获取数据
     */
    fun requestData(type: Int, page: Int) {

        var observer: Observer<Page<AppInfo>>? = null

        if (page == 0) {
            observer = object : ProgressObserver<Page<AppInfo>>(mContext, mView) {
                override fun onNext(infoPage: Page<AppInfo>) {
                    mPerView.showResult(infoPage)
                }
            }
        } else {
            observer = object : ErrorHandlerObserver<Page<AppInfo>>(mContext) {
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(infoPage: Page<AppInfo>) {
                    mPerView.showResult(infoPage)
                }

                override fun onComplete() {
                    mPerView.onLoadMoreComplete()
                }
            }
        }

        val observable: Observable<BaseReq<Page<AppInfo>>> = when (type) {
            TOP_LIST -> mPerModel.topList(page)
            GAME -> mPerModel.games(page)
            HOT_APP_LIST -> mPerModel.getHotApps(page)
            else -> Observable.empty<BaseReq<Page<AppInfo>>>()
        }
        observable.compatResult().subscribe(observer)
    }

}