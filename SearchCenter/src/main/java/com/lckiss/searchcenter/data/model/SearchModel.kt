package com.lckiss.searchcenter.data.model

import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.searchcenter.data.api.SearchApi
import com.lckiss.searchcenter.data.protocol.SearchResp
import com.lckiss.searchcenter.presenter.contract.SearchContract
import io.reactivex.Observable

/**
 * 搜索模块M层
 */
class SearchModel(private val searchApi: SearchApi) : SearchContract.ISearchModel {

    /**
     * 获取搜索建议
     */
    override fun getSuggestion(keyword: String): Observable<BaseReq<List<String>>> {
        return searchApi.searchSuggest(keyword)

    }

    /**
     * 搜索
     */
    override fun search(keyword: String): Observable<BaseReq<SearchResp>> {
        return searchApi.search(keyword)
    }
}