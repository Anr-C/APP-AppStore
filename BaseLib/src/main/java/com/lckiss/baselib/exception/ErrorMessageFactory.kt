package com.lckiss.baselib.exception

import android.content.Context
import android.net.sip.SipErrorCode.SOCKET_ERROR
import com.lckiss.baselib.R
import com.lckiss.baselib.common.BaseConstant.Companion.ERROR_API_ACCOUNT_FREEZE
import com.lckiss.baselib.common.BaseConstant.Companion.ERROR_API_LOGIN
import com.lckiss.baselib.common.BaseConstant.Companion.ERROR_API_NO_PERMISSION
import com.lckiss.baselib.common.BaseConstant.Companion.ERROR_API_SYSTEM
import com.lckiss.baselib.common.BaseConstant.Companion.ERROR_HTTP_400
import com.lckiss.baselib.common.BaseConstant.Companion.ERROR_HTTP_404
import com.lckiss.baselib.common.BaseConstant.Companion.ERROR_HTTP_500
import com.lckiss.baselib.common.BaseConstant.Companion.ERROR_TOKEN
import com.lckiss.baselib.common.BaseConstant.Companion.HTTP_ERROR
import com.lckiss.baselib.common.BaseConstant.Companion.LOST_TOKEN
import com.lckiss.baselib.common.BaseConstant.Companion.SOCKET_TIMEOUT_ERROR

/**
 * 异常工厂 根据code返回对应提示
 */
class ErrorMessageFactory {
    companion object {
        fun create(context: Context, code: Int): String {
            val errorMsg: String
            when (code) {
            //BaseException
                HTTP_ERROR -> errorMsg = context.resources.getString(R.string.error_http)
                SOCKET_TIMEOUT_ERROR -> errorMsg = context.resources.getString(R.string.error_socket_timeout)
                SOCKET_ERROR -> errorMsg = context.resources.getString(R.string.error_socket_unreachable)
                ERROR_HTTP_400 -> errorMsg = context.resources.getString(R.string.error_http_400)
                ERROR_HTTP_404 -> errorMsg = context.resources.getString(R.string.error_http_404)
                ERROR_HTTP_500 -> errorMsg = context.resources.getString(R.string.error_http_500)
            //ApiException
                ERROR_API_SYSTEM -> errorMsg = context.resources.getString(R.string.error_system)
                ERROR_API_ACCOUNT_FREEZE -> errorMsg = context.resources.getString(R.string.error_account_freeze)
                ERROR_API_NO_PERMISSION -> errorMsg = context.resources.getString(R.string.error_api_no_perission)
                ERROR_API_LOGIN -> errorMsg = context.resources.getString(R.string.error_login)
                ERROR_TOKEN -> errorMsg = context.resources.getString(R.string.error_token)
                LOST_TOKEN -> errorMsg = context.resources.getString(R.string.lost_token)
                else -> errorMsg = context.resources.getString(R.string.error_unkown)
            }
            return errorMsg
        }
    }

}