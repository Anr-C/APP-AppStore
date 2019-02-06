package com.lckiss.appstore.data.api

import com.lckiss.provider.data.protocol.AppInfo
import com.lckiss.baselib.data.protocol.BaseReq
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * MainApi
 */
interface MainApi {

    /*
        获取更新信息
     */
    @GET("apps/updateinfo")
    abstract fun getAppsUpdateinfo(@Query("packageName") packageName: String, @Query("versionCode") versionCode: String): Observable<BaseReq<List<AppInfo>>>

}