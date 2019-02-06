package com.lckiss.appstore.injection.component

import com.lckiss.appstore.injection.module.MainModule
import com.lckiss.appstore.ui.activity.MainActivity
import com.lckiss.baselib.injection.ActivityScope
import com.lckiss.baselib.injection.component.AppComponent
import dagger.Component

/**
 * MainComponent定义
 */
@ActivityScope
@Component(modules = [(MainModule::class)], dependencies = [(AppComponent::class)])
interface MainComponent {
    fun inject(activity: MainActivity)
}
