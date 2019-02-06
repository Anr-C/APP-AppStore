package com.lckiss.pagecenter.presenter

import com.lckiss.baselib.common.compatResult
import com.lckiss.baselib.presenter.BasePresenter
import com.lckiss.baselib.rx.observer.ErrorHandlerObserver
import com.lckiss.baselib.rx.observer.ProgressObserver
import com.lckiss.pagecenter.data.protocol.Page
import com.lckiss.pagecenter.data.protocol.Subject
import com.lckiss.pagecenter.data.protocol.SubjectDetail
import com.lckiss.pagecenter.presenter.contarct.SubjectContract
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * 主题界面业务逻辑
 */
class SubjectPresenter @Inject constructor(mModel: SubjectContract.ISubjectModel, mView: SubjectContract.ISubjectView) : BasePresenter<SubjectContract.ISubjectModel, SubjectContract.ISubjectView>(mModel, mView) {

    val mPerModel: SubjectContract.ISubjectModel = mModel
    val mPerView: SubjectContract.ISubjectView = mView

    /**
     * 获取主题数据
     */
    fun getSubjects(page: Int) {
        val subscriber: Observer<Page<Subject>>

        if (page == 0) {
            subscriber = object : ProgressObserver<Page<Subject>>(mContext, mPerView) {
                override fun onNext(pageBean: Page<Subject>) {
                    mPerView.showSubjects(pageBean)
                }
            }
        } else {
            subscriber = object : ErrorHandlerObserver<Page<Subject>>(mContext) {
                override fun onComplete() {
                    mPerView.onLoadMoreComplete()
                }

                override fun onSubscribe(d: Disposable) {}

                override fun onNext(pageBean: Page<Subject>) {
                    mPerView.showSubjects(pageBean)
                }
            }
        }

        mPerModel.getSubjects(page).compatResult().subscribe(subscriber)
    }

    /**
     * 获取主题详情
     */
    fun getSubjectDetail(id: Int) {
        mPerModel.getSubjectDetail(id).compatResult()
                .subscribe(object : ProgressObserver<SubjectDetail>(mContext, mPerView) {
                    override fun onNext(subjectDetail: SubjectDetail) {
                        mPerView.showSubjectDetail(subjectDetail)
                    }
                })
    }
}