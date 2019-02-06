package com.lckiss.pagecenter.presenter.contarct

import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.baselib.presenter.view.BaseView
import com.lckiss.pagecenter.data.protocol.Category
import com.lckiss.pagecenter.data.protocol.Page
import com.lckiss.provider.data.protocol.AppInfo
import io.reactivex.Observable

/**
 * 分类抽象层
 */
interface CategoryContract {

    interface ICategoryModel {

        val categories: Observable<BaseReq<List<Category>>>
        /**
         * 根据分类获取推荐
         */
        fun getFeaturedAppsByCategory(categoryid: Int, page: Int): Observable<BaseReq<Page<AppInfo>>>

        /**
         * 根据分类获取排行
         */
        fun getTopListAppsByCategory(categoryid: Int, page: Int): Observable<BaseReq<Page<AppInfo>>>

        /**
         * 根据分类获取新上架APP
         */
        fun getNewListAppsByCategory(categoryid: Int, page: Int): Observable<BaseReq<Page<AppInfo>>>

    }

    interface CategoryView : BaseView {
        fun showData(categories: List<Category>){}
        fun showResult(page: Page<AppInfo>){}
        fun onLoadMoreComplete(){}
    }
}