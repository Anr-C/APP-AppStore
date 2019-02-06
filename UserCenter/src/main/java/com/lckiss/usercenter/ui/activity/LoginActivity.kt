package com.lckiss.usercenter.ui.activity

import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import com.alibaba.android.arouter.facade.annotation.Route
import com.lckiss.baselib.common.BaseApplication
import com.lckiss.baselib.ui.activity.BaseActivity
import com.lckiss.baselib.router.RouterPath
import com.lckiss.usercenter.R
import com.lckiss.usercenter.data.protocol.LoginResp
import com.lckiss.usercenter.injection.component.DaggerLoginComponent
import com.lckiss.usercenter.injection.module.LoginModule
import com.lckiss.usercenter.presenter.LoginPresenter
import com.lckiss.usercenter.presenter.contract.LoginContract
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.ionicons_typeface_library.Ionicons
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登录界面
 */
@Route(path = RouterPath.UserCenter.PATH_LOGIN)
class LoginActivity : BaseActivity<LoginPresenter>(),LoginContract.LoginView {
    override fun init() {
        mToolBar.navigationIcon = IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_ios_arrow_back)
                .sizeDp(16)
                .color(ContextCompat.getColor(this,R.color.md_white_1000))

        mToolBar.setNavigationOnClickListener{ this.finish()}

        val mMobiObservable =
                Observable.create<String> {
                    mMobiEt.addTextChangedListener(object :TextWatcher{
                        override fun afterTextChanged(s: Editable?) {
                        }

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            it.onNext(s.toString())
                        }
                    })
                }

        val mPasswordObservable =
                Observable.create<String> {
                    mPasswordEt.addTextChangedListener(object :TextWatcher{
                        override fun afterTextChanged(s: Editable?) {
                        }

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            it.onNext(s.toString())
                        }
                    })
                }

        //监听字符变动
        Observable.combineLatest(mMobiObservable,mPasswordObservable, BiFunction<CharSequence,CharSequence,Boolean>{
            mobi,pwd->
            isPhoneValid(mobi.toString()) && isPasswordValid(pwd.toString())
        }).subscribe{
            mBtnLogin.isEnabled=it
        }

        mBtnLogin.setOnClickListener {
            mPresenter.login(mMobiEt.text.toString(),mPasswordEt.text.toString())
        }
    }

    override fun injectComponent() {
    DaggerLoginComponent.builder().appComponent((application as BaseApplication).appComponent)
            .loginModule(LoginModule(this)).build().inject(this)
    }

    override fun setLayout(): Int {
        return R.layout.activity_login
    }

    /**
     * 按钮特性
     */
    override fun showLoading() {
        mBtnLogin.showLoading()
    }

    override fun dismissLoading() {
        mBtnLogin.showButtonText()
    }

    override fun showError(msg: String) {
        mBtnLogin.showButtonText()
    }

    /**
     * MD控件三毛特效
     */
    override fun checkPhoneError() {
        mViewMobiWrapper.error="手机号码输入错误"
    }

    override fun checkPwdError() {
        mViewPasswordWrapper.error="密码不能为空"
    }

    override fun checkPhoneSuccess() {
        mViewMobiWrapper.error=""
        mViewMobiWrapper.isErrorEnabled=false
    }

    override fun checkPwdSuccess() {
        mViewPasswordWrapper.error=""
        mViewPasswordWrapper.isErrorEnabled=false
    }

    override fun loginSuccess(loginResp: LoginResp) {
        this.finish()
    }

    /**
     * 验证字段合法性
     */
    private fun isPhoneValid(phone: String): Boolean {
        return phone.length == 11
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }

}