package com.lckiss.baselib.rx

import android.content.Context
import android.widget.Toast
import com.lckiss.baselib.common.BaseConstant.Companion.HTTP_ERROR
import com.lckiss.baselib.common.BaseConstant.Companion.SOCKET_ERROR
import com.lckiss.baselib.common.BaseConstant.Companion.SOCKET_TIMEOUT_ERROR
import com.lckiss.baselib.common.BaseConstant.Companion.UNKNOWN_ERROR
import com.lckiss.baselib.exception.ApiException
import com.lckiss.baselib.exception.BaseException
import com.lckiss.baselib.exception.ErrorMessageFactory
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException

/**
 * RxErrorHandler错误处理
 */
class RxErrorHandler(val context: Context) {
    fun handleError(throwable: Throwable): BaseException {
        val exception = BaseException(UNKNOWN_ERROR, "不好意思，应用出现未知错误~")
        when (throwable) {
            is ApiException -> {
                exception.code = throwable.code
            }
            is SocketException -> {
                exception.code = SOCKET_ERROR
            }
            is SocketTimeoutException -> {
                exception.code = SOCKET_TIMEOUT_ERROR
            }
            is HttpException -> {
                exception.code = HTTP_ERROR
            }
            else -> {
                exception.code = UNKNOWN_ERROR
                throwable.printStackTrace()
            }
        }
        exception.displayMessage = ErrorMessageFactory.create(context, exception.code)
        return exception
    }

    fun showErrorMessage(exception: BaseException) {
        Toast.makeText(context, exception.displayMessage, Toast.LENGTH_SHORT).show()
    }

}