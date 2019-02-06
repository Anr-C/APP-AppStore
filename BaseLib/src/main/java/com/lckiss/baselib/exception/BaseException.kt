package com.lckiss.baselib.exception

import java.lang.Exception

/**
 * 通用异常定义
 */
open class BaseException(var code: Int, var displayMessage: String): Exception()