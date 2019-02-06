package com.lckiss.pagecenter.data.model

import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.pagecenter.data.api.SubjectApi
import com.lckiss.pagecenter.data.protocol.Page
import com.lckiss.pagecenter.data.protocol.Subject
import com.lckiss.pagecenter.data.protocol.SubjectDetail
import com.lckiss.pagecenter.presenter.contarct.SubjectContract
import io.reactivex.Observable

/**
 * 主题M层
 */
class SubjectModel(private val subjectApi: SubjectApi) : SubjectContract.ISubjectModel {

    override fun getSubjects(page: Int): Observable<BaseReq<Page<Subject>>> {
        return subjectApi.subjects(page)
    }

    override fun getSubjectDetail(id: Int): Observable<BaseReq<SubjectDetail>> {
        return subjectApi.subjectDetail(id)
    }
}
