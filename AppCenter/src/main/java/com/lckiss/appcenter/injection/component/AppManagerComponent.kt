package com.lckiss.appcenter.injection.component

import com.lckiss.appcenter.injection.module.AppManagerModule
import com.lckiss.appcenter.ui.fragment.AppManagerFragment
import com.lckiss.baselib.injection.PerComponentScope
import com.lckiss.baselib.injection.component.AppComponent
import dagger.Component

/**
 * AppManagerComponent
 */
@PerComponentScope
@Component(modules = [(AppManagerModule::class)], dependencies = [(AppComponent::class)])
interface AppManagerComponent {
    fun inject(fragment: AppManagerFragment)
}
