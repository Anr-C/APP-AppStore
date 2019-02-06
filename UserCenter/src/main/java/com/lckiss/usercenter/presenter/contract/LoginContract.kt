package com.lckiss.usercenter.presenter.contract

import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.baselib.presenter.view.BaseView
import com.lckiss.usercenter.data.protocol.LoginResp
import io.reactivex.Observable

/**
 * LoginContract定义
 */
interface LoginContract {
    /**
     * Model抽象接口定义
     */
    interface ILoginModel {
        fun login(phone: String, pwd: String): Observable<BaseReq<LoginResp>>
    }

    /**
     * View抽象接口定义
     */
    interface LoginView : BaseView {
        fun checkPhoneError()
        fun checkPhoneSuccess()
        fun checkPwdError()
        fun checkPwdSuccess()
        fun loginSuccess(loginResp: LoginResp)
    }
}