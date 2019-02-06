package com.lckiss.usercenter.data.protocol
import java.io.Serializable;

/**
 * 登录请求体
 */
data class LoginReq(val email:String,val password:String):Serializable