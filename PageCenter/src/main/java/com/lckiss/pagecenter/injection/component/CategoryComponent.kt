package com.lckiss.pagecenter.injection.component

import com.lckiss.baselib.injection.PerComponentScope
import com.lckiss.baselib.injection.component.AppComponent
import com.lckiss.pagecenter.injection.module.CategoryModule
import com.lckiss.pagecenter.ui.fragment.CategoryAppFragment
import com.lckiss.pagecenter.ui.fragment.CategoryFragment
import dagger.Component

/**
 * CategoryComponent
 */
@PerComponentScope
@Component(modules = arrayOf(CategoryModule::class), dependencies = arrayOf(AppComponent::class))
interface CategoryComponent {
    fun inject(fragment: CategoryFragment)
    fun inject(fragment: CategoryAppFragment)
}
