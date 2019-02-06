package com.lckiss.pagecenter.data.api

import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.pagecenter.data.protocol.Index
import com.lckiss.pagecenter.data.protocol.Page
import com.lckiss.provider.data.protocol.AppInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 页面请求Api
 */
interface AppInfoApi {

    @GET("index")
    fun index(): Observable<BaseReq<Index>>

    @GET("toplist")
    fun topList(@Query("page") page: Int): Observable<BaseReq<Page<AppInfo>>>

    @GET("game")
    fun games(@Query("page") page: Int): Observable<BaseReq<Page<AppInfo>>>

    //App详情和热门

    @GET("app/{id}")
    fun getAppDetail(@Path("id") id: Int): Observable<BaseReq<AppInfo>>

    @GET("app/hot")
    fun getHotApps(@Query("page") page: Int): Observable<BaseReq<Page<AppInfo>>>

}