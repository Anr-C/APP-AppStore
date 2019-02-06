package com.lckiss.searchcenter.data.api

import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.searchcenter.data.protocol.SearchResp
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 搜索Api
 */
interface SearchApi{

    @GET("search/suggest")
    abstract fun searchSuggest(@Query("keyword") keyword: String): Observable<BaseReq<List<String>>>

    @GET("search")
    abstract fun search(@Query("keyword") keyword: String): Observable<BaseReq<SearchResp>>
}