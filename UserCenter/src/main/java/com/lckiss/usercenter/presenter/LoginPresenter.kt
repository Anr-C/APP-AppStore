package com.lckiss.usercenter.presenter

import com.hwangjr.rxbus.RxBus
import com.lckiss.baselib.common.BaseConstant
import com.lckiss.baselib.common.BaseConstant.Companion.TOKEN
import com.lckiss.baselib.common.BaseConstant.Companion.USER
import com.lckiss.baselib.common.compatResult
import com.lckiss.baselib.presenter.BasePresenter
import com.lckiss.baselib.rx.observer.ErrorHandlerObserver
import com.lckiss.baselib.utils.ACache
import com.lckiss.baselib.utils.VerificationUtils
import com.lckiss.usercenter.data.protocol.LoginResp
import com.lckiss.usercenter.presenter.contract.LoginContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * 登录模块Presenter
 */
class LoginPresenter : BasePresenter<LoginContract.ILoginModel, LoginContract.LoginView> {

    @Inject
    constructor(mModel: LoginContract.ILoginModel, mView: LoginContract.LoginView) : super(mModel, mView)

    /**
     * 登录方法
     */
    fun login(phone: String, pwd: String) {
        if (!VerificationUtils.matcherPhoneNum(phone)) {
            mView.checkPhoneError()
            return
        } else {
            mView.checkPhoneSuccess()
        }

        if (!pwd.isNotEmpty()) {
            mView.checkPwdError()
            return
        } else {
            mView.checkPwdSuccess()
        }


        mModel!!.login(phone, pwd).compatResult()
                .subscribe(object : ErrorHandlerObserver<LoginResp>(mContext) {
                    override fun onSubscribe(d: Disposable) {
                        mView.showLoading()
                    }

                    override fun onNext(loginResp: LoginResp) {
                        mView.loginSuccess(loginResp)
                        saveUser(loginResp)
                        //登录成功发送通知
                        RxBus.get().post(BaseConstant.Companion.EventType.TAG_LOGIN, loginResp.user)
                    }

                    override fun onComplete() {
                        mView.dismissLoading()
                    }

                    override fun onError(throwable: Throwable) {
                        super.onError(throwable)
                        mView.dismissLoading()
                    }
                })

    }

    /**
     * 将登录成功后的数据保存到硬盘
     */
    private fun saveUser(loginResp: LoginResp) {
        val aCache = ACache.get(mContext)
        aCache.put(TOKEN, loginResp.token)
        aCache.put(USER, loginResp.user)
    }
}