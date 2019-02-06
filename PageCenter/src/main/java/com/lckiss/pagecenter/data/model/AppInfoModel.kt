package com.lckiss.pagecenter.data.model

import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.pagecenter.data.api.AppInfoApi
import com.lckiss.pagecenter.data.protocol.Index
import com.lckiss.pagecenter.data.protocol.Page
import com.lckiss.provider.data.protocol.AppInfo
import io.reactivex.Observable

/**
 * 所有页面请求处理Model
 */
class AppInfoModel(private val mAppInfoApi: AppInfoApi) {
    /**
     * 首页数据
     */
    fun index(): Observable<BaseReq<Index>> {
        return mAppInfoApi.index()
    }

    /**
     * 排行数据
     */
    fun topList(page: Int): Observable<BaseReq<Page<AppInfo>>> {
        return mAppInfoApi.topList(page)
    }

    /**
     * 游戏数据
     */
    fun games(page: Int): Observable<BaseReq<Page<AppInfo>>> {
        return mAppInfoApi.games(page)
    }


    /**
     * 获取应用详情
     */
    fun getAppDetail(id: Int): Observable<BaseReq<AppInfo>> {
        return mAppInfoApi.getAppDetail(id)
    }


    /**
     * 热门数据
     */
    fun getHotApps(page: Int): Observable<BaseReq<Page<AppInfo>>> {
        return mAppInfoApi.getHotApps(page)
    }
}