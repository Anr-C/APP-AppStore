package com.lckiss.pagecenter.injection.component

import com.lckiss.baselib.injection.PerComponentScope
import com.lckiss.baselib.injection.component.AppComponent
import com.lckiss.pagecenter.injection.module.AppDetailModule
import com.lckiss.pagecenter.ui.fragment.AppDetailFragment
import dagger.Component

/**
 * AppDetailComponent
 */
@PerComponentScope
@Component(modules = arrayOf(AppDetailModule::class), dependencies = arrayOf(AppComponent::class))
interface AppDetailComponent {
    fun inject(fragment: AppDetailFragment)
}