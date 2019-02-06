package com.lckiss.usercenter.data.model

import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.usercenter.data.api.LoginApi
import com.lckiss.usercenter.data.protocol.LoginReq
import com.lckiss.usercenter.data.protocol.LoginResp
import com.lckiss.usercenter.presenter.contract.LoginContract
import io.reactivex.Observable

/**
 * LoginModel定义
 */

class LoginModel(val loginApi: LoginApi) : LoginContract.ILoginModel {

    override fun login(phone: String, pwd: String): Observable<BaseReq<LoginResp>> {
        val param = LoginReq(phone,pwd)
        return loginApi.login(param)
    }
}