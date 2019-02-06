package com.lckiss.pagecenter.presenter

import com.lckiss.baselib.common.compatResult
import com.lckiss.baselib.data.protocol.BaseReq
import com.lckiss.baselib.presenter.BasePresenter
import com.lckiss.baselib.rx.observer.ErrorHandlerObserver
import com.lckiss.baselib.rx.observer.ProgressObserver
import com.lckiss.pagecenter.data.protocol.Category
import com.lckiss.pagecenter.data.protocol.Page
import com.lckiss.pagecenter.presenter.contarct.CategoryContract
import com.lckiss.provider.data.protocol.AppInfo
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * 分类逻辑层
 */
class CategoryPresenter @Inject
constructor(mModel: CategoryContract.ICategoryModel, mView: CategoryContract.CategoryView)
    : BasePresenter<CategoryContract.ICategoryModel, CategoryContract.CategoryView>(mModel, mView) {

    var mPerView: CategoryContract.CategoryView = mView
    var mPerModel: CategoryContract.ICategoryModel = mModel

    companion object {
        const val FEATURED = 0
        const val TOPLIST = 1
        const val NEWLIST = 2
    }

    /**
     * 获取所有的分类
     */
    fun getAllCategory() {
        mPerModel.categories.compatResult()
                .subscribe(object : ProgressObserver<List<Category>>(mContext, mView) {
                    override fun onNext(categories: List<Category>) {
                        mPerView.showData(categories)
                    }
                })

    }

    /**
     * 按分类获取数据
     */
    fun requestCategoryApps(page: Int,  flagType: Int,categoryId: Int) {

        var observer: Observer<Page<AppInfo>>? = null

        if (page == 0) {
            observer = object : ProgressObserver<Page<AppInfo>>(mContext, mView) {
                override fun onNext(appInfoPageBean: Page<AppInfo>) {
                    mPerView.showResult(appInfoPageBean)
                }
            }
        } else {
            observer = object : ErrorHandlerObserver<Page<AppInfo>>(mContext) {
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(appInfoPageBean: Page<AppInfo>) {
                    mPerView.showResult(appInfoPageBean)
                }

                override fun onComplete() {
                    mPerView.onLoadMoreComplete()
                }
            }
        }

        val observable: Observable<BaseReq<Page<AppInfo>>> = when (flagType) {
            FEATURED -> {
                mPerModel.getFeaturedAppsByCategory(categoryId, page)
            }
            TOPLIST -> {
                mPerModel.getTopListAppsByCategory(categoryId, page)
            }
            NEWLIST -> {
                mPerModel.getNewListAppsByCategory(categoryId, page)
            }
            else -> Observable.empty<BaseReq<Page<AppInfo>>>()
        }
        observable.compatResult().subscribe(observer)
    }
}
