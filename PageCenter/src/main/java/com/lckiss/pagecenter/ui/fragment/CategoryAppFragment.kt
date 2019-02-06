package com.lckiss.pagecenter.ui.fragment

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.lckiss.baselib.injection.component.AppComponent
import com.lckiss.baselib.ui.fragment.ProgressFragment
import com.lckiss.pagecenter.R
import com.lckiss.pagecenter.data.protocol.Page
import com.lckiss.pagecenter.injection.component.DaggerCategoryComponent
import com.lckiss.pagecenter.injection.module.CategoryModule
import com.lckiss.pagecenter.presenter.CategoryPresenter
import com.lckiss.pagecenter.presenter.contarct.CategoryContract
import com.lckiss.provider.ui.adapter.AppInfoAdapter
import com.lckiss.provider.ui.widget.DividerItemDecoration
import com.lckiss.provider.data.protocol.AppInfo
import kotlinx.android.synthetic.main.template_recycle_view.*

/**
 * 分类
 */
class CategoryAppFragment : ProgressFragment<CategoryPresenter>(), CategoryContract.CategoryView, BaseQuickAdapter.RequestLoadMoreListener {

    companion object {

        fun newInstance(categoryId: Int, fragmentType: Int): CategoryAppFragment {
            val f = CategoryAppFragment()
            f.setArguments(categoryId, fragmentType)
            return f
        }
    }

    private var categoryId: Int = 0
    private var flagType: Int = 0

    internal var pageNum = 0

    protected lateinit var mAppInfoAdapter: AppInfoAdapter


    private fun setArguments(categoryId: Int, fragmentType: Int) {
        this.categoryId = categoryId
        this.flagType = fragmentType
    }

    override fun init() {
        mPresenter.requestCategoryApps(pageNum, flagType, categoryId)

        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        val itemDecoration = DividerItemDecoration(activity as Context, DividerItemDecoration.VERTICAL_LIST)
        mRecyclerView.addItemDecoration(itemDecoration)

        mAppInfoAdapter = when (flagType) {
            CategoryPresenter.TOPLIST -> {
                AppInfoAdapter.builder().showPosition(true)
                        .showCategoryName(false).showBrief(true).retrofit(mRetrofit).build()
            }
            else -> {
                AppInfoAdapter.builder().showPosition(false)
                        .showCategoryName(false).showBrief(true).retrofit(mRetrofit).build()

            }
        }

        mAppInfoAdapter.setOnLoadMoreListener(this, mRecyclerView)
        mRecyclerView.adapter = mAppInfoAdapter

        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                //context.startActivity<AppDetailActivity>("appinfo" to mAppInfoAdapter.getItem(position))
                mApplication.cacheView = view
            }
        })
    }

    override fun onLoadMoreRequested() {
        mPresenter.requestCategoryApps(pageNum, flagType, categoryId)
    }

    override fun injectComponent(appComponent: AppComponent) {
        DaggerCategoryComponent.builder().appComponent(appComponent)
                .categoryModule(CategoryModule(this)).build().inject(this)
    }

    override fun setLayout(): Int {
        return R.layout.template_recycle_view
    }

    override fun showResult(page: Page<AppInfo>) {
        mAppInfoAdapter.addData(page.datas)
        if (page.hasMore) {
            pageNum++
        }
        mAppInfoAdapter.setEnableLoadMore(page.hasMore)
    }

    override fun onLoadMoreComplete() {
        mAppInfoAdapter.loadMoreComplete()
    }

    override fun getOnclickListener(): View.OnClickListener {
        return View.OnClickListener{
            mPresenter.requestCategoryApps(pageNum, flagType, categoryId)
        }
    }
}