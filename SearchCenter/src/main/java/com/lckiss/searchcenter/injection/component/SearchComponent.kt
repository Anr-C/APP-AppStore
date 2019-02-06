package com.lckiss.searchcenter.injection.component

import com.lckiss.baselib.injection.PerComponentScope
import com.lckiss.baselib.injection.component.AppComponent
import com.lckiss.searchcenter.injection.module.SearchModule
import com.lckiss.searchcenter.ui.activity.SearchActivity
import dagger.Component

/**
 * SearchComponent
 */
@PerComponentScope
@Component(modules = [(SearchModule::class)], dependencies = [(AppComponent::class)])
interface SearchComponent {
    fun inject(activity: SearchActivity)
}
