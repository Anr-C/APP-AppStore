package com.lckiss.baselib.exception

/**
 * Api异常定义
 */
class ApiException(code: Int, displayMessage: String) : BaseException(code, displayMessage)