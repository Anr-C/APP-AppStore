package com.lckiss.pagecenter.injection.component

import com.lckiss.baselib.injection.PerComponentScope
import com.lckiss.baselib.injection.component.AppComponent
import com.lckiss.pagecenter.injection.module.RecommendModule
import com.lckiss.pagecenter.ui.fragment.RecommendFragment
import dagger.Component

/**
 * 推荐页Component
 */
@PerComponentScope
@Component(modules = arrayOf(RecommendModule::class), dependencies = arrayOf(AppComponent::class))
interface RecommendComponent {
    //关联fragment
    fun inject(fragment: RecommendFragment)
}
