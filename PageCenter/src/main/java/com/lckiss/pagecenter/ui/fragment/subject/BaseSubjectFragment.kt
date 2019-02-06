package com.lckiss.pagecenter.ui.fragment.subject

import com.lckiss.baselib.injection.component.AppComponent
import com.lckiss.baselib.ui.fragment.ProgressFragment
import com.lckiss.pagecenter.data.protocol.Page
import com.lckiss.pagecenter.data.protocol.Subject
import com.lckiss.pagecenter.data.protocol.SubjectDetail
import com.lckiss.pagecenter.injection.component.DaggerSubjectComponent
import com.lckiss.pagecenter.injection.module.SubjectModule
import com.lckiss.pagecenter.presenter.SubjectPresenter
import com.lckiss.pagecenter.presenter.contarct.SubjectContract

/**
 * 主题界面抽象V
 */
abstract class BaseSubjectFragment : ProgressFragment<SubjectPresenter>(), SubjectContract.ISubjectView{

    override fun onLoadMoreComplete() {
    }

    override fun showSubjects(subjects: Page<Subject>) {
    }

    override fun showSubjectDetail(detail: SubjectDetail) {
    }

    override fun injectComponent(appComponent: AppComponent) {
        DaggerSubjectComponent.builder().appComponent(appComponent)
                .subjectModule(SubjectModule(this)).build().inject(this)
    }
}