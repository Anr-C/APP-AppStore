package com.lckiss.baselib.data.protocol

import java.io.Serializable

/**
 * 通用响应对象
 * @status:响应状态码
 * @message:响应文字消息
 * @data:具体响应业务对象
 */
data class BaseReq<out T>(val status:Int, val message:String, val data:T ):Serializable