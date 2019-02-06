package com.lckiss.pagecenter.data.api

import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.pagecenter.data.protocol.Page
import com.lckiss.pagecenter.data.protocol.Subject
import com.lckiss.pagecenter.data.protocol.SubjectDetail
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * SubjectApi
 */
interface SubjectApi {

    @GET("subject/hot")
    abstract fun subjects(@Query("page") page: Int): Observable<BaseReq<Page<Subject>>>

    @GET("subject/{id}")
    abstract fun subjectDetail(@Path("id") id: Int): Observable<BaseReq<SubjectDetail>>
}