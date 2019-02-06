package com.lckiss.usercenter.data.protocol
import java.io.Serializable;

/**
 * 登录返回实体
 */
data class LoginResp(val token:String, val user:User):Serializable