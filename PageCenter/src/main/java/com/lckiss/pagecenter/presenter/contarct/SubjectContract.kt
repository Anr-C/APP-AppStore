package com.lckiss.pagecenter.presenter.contarct

import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.baselib.presenter.view.BaseView
import com.lckiss.pagecenter.data.protocol.Page
import com.lckiss.pagecenter.data.protocol.Subject
import com.lckiss.pagecenter.data.protocol.SubjectDetail
import io.reactivex.Observable

/**
 * 主题业务抽象层
 */
interface SubjectContract {

    interface ISubjectView : BaseView {
        /**
         * 展示主题
         */
        fun showSubjects(subjects: Page<Subject>)

        /**
         * 加载更多完成
         */
        fun onLoadMoreComplete()

        /**
         * 展示主题详情
         */
        fun showSubjectDetail(detail: SubjectDetail)

    }

    interface ISubjectModel {
        /**
         * 获取全部主题
         */
        fun getSubjects(page: Int): Observable<BaseReq<Page<Subject>>>

        /**
         * 获取主题详情
         */
        fun getSubjectDetail(id: Int): Observable<BaseReq<SubjectDetail>>

    }
}