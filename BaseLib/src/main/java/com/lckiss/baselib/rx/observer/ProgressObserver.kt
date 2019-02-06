package com.lckiss.baselib.rx.observer

import android.content.Context
import com.lckiss.baselib.presenter.view.BaseView
import io.reactivex.disposables.Disposable

/**
 * 通用进度条监听
 */
abstract class ProgressObserver<T>(var mContext: Context, var mView: BaseView) : ErrorHandlerObserver<T>(mContext) {

    /**
     * 开始
     */
    override fun onSubscribe(d: Disposable) {
        mView.showLoading()
    }

    /**
     * 结束
     */
    override fun onComplete() {
        mView.dismissLoading()
    }

    /**
     * 错误
     */
    override fun onError(throwable: Throwable) {
        val baseException = mErrorHandler.handleError(throwable)
        mView.showError(baseException.displayMessage)
    }
}