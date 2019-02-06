package com.lckiss.baselib.ui.activity

import android.os.Bundle
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.app.AppCompatActivity
import com.lckiss.baselib.common.BaseApplication
import com.lckiss.baselib.presenter.BasePresenter
import com.lckiss.baselib.presenter.view.BaseView
import com.mikepenz.iconics.context.IconicsLayoutInflater2
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * 登录界面
 */
abstract class BaseActivity<T : BasePresenter<*,*>> : AppCompatActivity(), BaseView {

    @Inject
    lateinit var mPresenter: T

    @Inject
    lateinit var mRetrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {

        LayoutInflaterCompat.setFactory2(layoutInflater, IconicsLayoutInflater2(delegate))

        super.onCreate(savedInstanceState)

        setContentView(setLayout())

        injectComponent()

        init()
    }

    /**
     * dagger注册
     */
    abstract fun injectComponent()

    /**
     * 初始化步骤
     */
    abstract fun init()

    /**
     * 布局设置
     */
    abstract fun setLayout(): Int

    override fun showLoading() {}

    override fun showError(msg: String) {}

    override fun dismissLoading() {}
}