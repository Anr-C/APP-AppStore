package com.lckiss.pagecenter.data.model

import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.pagecenter.data.api.CategoryApi
import com.lckiss.pagecenter.data.protocol.Category
import com.lckiss.pagecenter.data.protocol.Page
import com.lckiss.pagecenter.presenter.contarct.CategoryContract
import com.lckiss.provider.data.protocol.AppInfo
import io.reactivex.Observable

/**
 * 分类M层
 */
class CategoryModel(private val categoryApi: CategoryApi) : CategoryContract.ICategoryModel {

    /**
     * 获取全部分类
     */
    override val categories: Observable<BaseReq<List<Category>>>
        get() = categoryApi.getCategories()

    /**
     * 根据分类获取推荐
     */
    override fun getFeaturedAppsByCategory(categoryid: Int, page: Int): Observable<BaseReq<Page<AppInfo>>> {
        return categoryApi.getFeaturedAppsByCategory(categoryid, page)
    }

    /**
     * 根据分类获取排行
     */
    override fun getTopListAppsByCategory(categoryid: Int, page: Int): Observable<BaseReq<Page<AppInfo>>> {
        return categoryApi.getTopListAppsByCategory(categoryid, page)
    }

    /**
     * 根据分类获取新上架APP
     */
    override fun getNewListAppsByCategory(categoryid: Int, page: Int): Observable<BaseReq<Page<AppInfo>>> {
        return categoryApi.getNewListAppsByCategory(categoryid, page)
    }

}