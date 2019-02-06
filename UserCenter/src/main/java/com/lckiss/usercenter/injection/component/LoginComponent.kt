package com.lckiss.usercenter.injection.component

import com.lckiss.baselib.injection.ActivityScope
import com.lckiss.baselib.injection.component.AppComponent
import com.lckiss.usercenter.injection.module.LoginModule
import com.lckiss.usercenter.ui.activity.LoginActivity
import dagger.Component

/**
 * LoginComponent定义
 */
@ActivityScope
@Component(modules = arrayOf(LoginModule::class), dependencies = arrayOf(AppComponent::class))
interface LoginComponent {
    fun inject(activity: LoginActivity)
}
