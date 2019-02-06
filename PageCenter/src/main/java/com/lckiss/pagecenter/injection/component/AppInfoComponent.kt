package com.lckiss.pagecenter.injection.component

import com.lckiss.baselib.injection.ActivityScope
import com.lckiss.baselib.injection.PerComponentScope
import com.lckiss.baselib.injection.component.AppComponent
import com.lckiss.pagecenter.injection.module.AppInfoModule
import com.lckiss.pagecenter.ui.fragment.BaseAppInfoFragment
import dagger.Component

/**
 * AppInfoComponent
 */
@PerComponentScope
@Component(modules = arrayOf(AppInfoModule::class), dependencies = arrayOf(AppComponent::class))
interface AppInfoComponent {
    //关联fragment
    fun inject(fragment: BaseAppInfoFragment)
}