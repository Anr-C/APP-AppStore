package com.lckiss.baselib.common

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.lckiss.baselib.common.BaseConstant.Companion.SUCCESS
import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.baselib.exception.ApiException
import com.lckiss.baselib.exception.ErrorMessageFactory
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * 对通用的操作做一个扩展定义
 */

/**
 * 扩展方法：对所有rx的线程切换做一个封装
 */
fun <T> Observable<T>.execute(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

/**
 * 所有的结果处理
 */
fun <T> Observable<BaseReq<T>>.compatResult(): Observable<T> {
    return this.flatMap(Function<BaseReq<T>, Observable<T>>() {
        if (it.status == SUCCESS) {
             Observable.create { subscribe ->
                try {
                    subscribe.onNext(it.data)
                    subscribe.onComplete()
                } catch (e: Throwable) {
                    subscribe.onError(e)
                }
            }
        } else {
            Observable.error(ApiException(it.status, it.message))
        }
    }).execute()

}

/**
 * 用于非View层的请求结果处理
 */
fun <T> Observable<BaseReq<T>>.compatResultWithMethod(context: Context,method: (String) -> Unit): Observable<T> {
    return this.flatMap(Function<BaseReq<T>, Observable<T>>() {
        if (it.status == SUCCESS) {
            Observable.create { subscribe ->
                try {
                    subscribe.onNext(it.data)
                    subscribe.onComplete()
                } catch (e: Throwable) {
                    subscribe.onError(e)
                }
            }
        } else {
            Observable.create{
                subscribe->
                try {
                    val displayMessage = ErrorMessageFactory.create(context, it.status)
                    method(displayMessage)
                    subscribe.onComplete()
                } catch (e: Throwable) {
                    subscribe.onError(e)
                }
            }
        }
    })

}

/**
 * 隐藏软键盘
 */
fun EditText.hideKeyBoard() {
    val imm = this.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}