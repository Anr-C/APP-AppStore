package com.lckiss.usercenter.data.api

import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.usercenter.data.protocol.LoginReq
import com.lckiss.usercenter.data.protocol.LoginResp
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 登录请求Api
 */
interface LoginApi {

    /*
        @body会默认把类转换成json
     */
    @POST("login")
    fun login(@Body bean: LoginReq): Observable<BaseReq<LoginResp>>
}