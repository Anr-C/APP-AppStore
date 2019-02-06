package com.lckiss.baselib.rx.observer

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.lckiss.baselib.common.BaseConstant.Companion.ERROR_TOKEN
import com.lckiss.baselib.common.BaseConstant.Companion.LOST_TOKEN
import com.lckiss.baselib.router.RouterPath
import com.lckiss.baselib.rx.RxErrorHandler
import com.lckiss.baselib.ui.activity.BaseActivity
import org.jetbrains.anko.startActivity

/**
 * 错误处理监听者
 */
abstract class ErrorHandlerObserver<T>(val context: Context): DefaultObserver<T>() {
    var mErrorHandler: RxErrorHandler = RxErrorHandler(context)

    override fun onError(throwable: Throwable) {
        val baseException = mErrorHandler.handleError(throwable)
            mErrorHandler.showErrorMessage(baseException)
            if (baseException.code == ERROR_TOKEN || baseException.code == LOST_TOKEN) {
                toLogin()
            }
    }

    /**
     * 当TOKEN错误或者丢失时 直接跳转登录
     */
    private fun toLogin(){
        ARouter.getInstance().build(RouterPath.UserCenter.PATH_LOGIN).navigation()
    }
}