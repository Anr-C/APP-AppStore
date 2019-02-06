package com.lckiss.pagecenter.injection.component

import com.lckiss.baselib.injection.PerComponentScope
import com.lckiss.baselib.injection.component.AppComponent
import com.lckiss.pagecenter.injection.module.SubjectModule
import com.lckiss.pagecenter.ui.fragment.subject.BaseSubjectFragment
import dagger.Component

/**
 * SubjectComponent
 */
@PerComponentScope
@Component(modules = [(SubjectModule::class)], dependencies = [(AppComponent::class)])
interface SubjectComponent {
    fun inject(fragment: BaseSubjectFragment)
}