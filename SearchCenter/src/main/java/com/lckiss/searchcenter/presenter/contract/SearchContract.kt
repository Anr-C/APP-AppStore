package com.lckiss.searchcenter.presenter.contract

import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.baselib.presenter.view.BaseView
import com.lckiss.searchcenter.data.protocol.SearchResp
import io.reactivex.Observable

/**
 * 搜索抽象层
 */
interface SearchContract {

    interface ISearchView : BaseView {

        /**
         * 显示搜索历史
         */
        fun showSearchHistory(list: List<String>)

        /**
         * 显示搜索建议
         */
        fun showSuggestions(list: List<String>)

        /**
         * 显示搜索结果
         */
        fun showSearchResult(result: SearchResp)

    }


    interface ISearchModel {

        /**
         * 获取搜索建议
         */
        fun getSuggestion(keyword: String): Observable<BaseReq<List<String>>>

        /**
         * 搜索
         */
        fun search(keyword: String): Observable<BaseReq<SearchResp>>

    }
}
