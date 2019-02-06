package com.lckiss.pagecenter.data.api

import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.pagecenter.data.protocol.Category
import com.lckiss.pagecenter.data.protocol.Page
import com.lckiss.provider.data.protocol.AppInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 分类API
 */
interface CategoryApi {

    @GET("category")
    fun getCategories(): Observable<BaseReq<List<Category>>>

    @GET("category/featured/{categoryid}")
    fun getFeaturedAppsByCategory(@Path("categoryid") categoryid: Int, @Query("page") page: Int): Observable<BaseReq<Page<AppInfo>>>

    @GET("category/toplist/{categoryid}")
    fun getTopListAppsByCategory(@Path("categoryid") categoryid: Int, @Query("page") page: Int): Observable<BaseReq<Page<AppInfo>>>

    @GET("category/newlist/{categoryid}")
    fun getNewListAppsByCategory(@Path("categoryid") categoryid: Int, @Query("page") page: Int): Observable<BaseReq<Page<AppInfo>>>

}