package com.lckiss.searchcenter.presenter

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lckiss.baselib.common.compatResult
import com.lckiss.baselib.presenter.BasePresenter
import com.lckiss.baselib.rx.observer.ProgressObserver
import com.lckiss.searchcenter.data.protocol.SearchResp
import com.lckiss.searchcenter.presenter.contract.SearchContract
import java.util.ArrayList
import javax.inject.Inject

/**
 * 搜索界面业务逻辑
 */
class SearchPresenter @Inject constructor(mModel: SearchContract.ISearchModel, mView: SearchContract.ISearchView) : BasePresenter<SearchContract.ISearchModel, SearchContract.ISearchView>(mModel, mView) {

    companion object {
        private const val SHARE_APP_TAG = "SearchHistory"
    }

    private val mPerModel=mModel
    private val mPerView=mView
    private var mSearchHistory: MutableList<String> = ArrayList()
    private var mSharedPreferences: SharedPreferences
    private val mGson:Gson

    init {
        mSharedPreferences=mContext.getSharedPreferences(SHARE_APP_TAG, Context.MODE_PRIVATE)
        mGson=Gson()
    }

    /**
     * 获取建议
     */
    fun getSuggestions(keyword: String) {
        mPerModel.getSuggestion(keyword)
                .compatResult()
                .subscribe(object : ProgressObserver<List<String>>(mContext, mPerView) {
                    override fun onNext(suggestions: List<String>) {
                        mPerView.showSuggestions(suggestions)
                    }
                })
    }

    /**
     * 搜索
     */
    fun search(keyword: String) {
        //将搜索词存储
        if (mSearchHistory.contains(keyword)) {
            mSearchHistory.remove(keyword)
        }
        mSearchHistory.add(0, keyword)

        mSharedPreferences.edit().putString("history",  mGson.toJson(mSearchHistory)).apply()
        //调用搜索逻辑
        mPerModel.search(keyword)
                .compatResult()
                .subscribe(object : ProgressObserver<SearchResp>(mContext, mPerView) {
                    override fun onNext(searchResult: SearchResp) {
                        mPerView.showSearchResult(searchResult)
                    }
                })
    }

    /**
     * 获取搜索历史
     */
    fun showHistory() {
        val history = mSharedPreferences.getString("history", null)
        if (history != null) {
            mSearchHistory = mGson.fromJson(history, object : TypeToken<List<String>>() {}.type)
        }
        mPerView.showSearchHistory(mSearchHistory)
    }

    /**
     * 清理历史
     */
    fun cleanHistory() {
        mSearchHistory.clear()
        mSharedPreferences.edit().putString("history", null).apply()
    }
}