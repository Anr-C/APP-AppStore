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
import com.lckiss.pagecenter.injection.component.DaggerAppInfoComponent
import com.lckiss.pagecenter.injection.module.AppInfoModule
import com.lckiss.pagecenter.presenter.AppInfoPresenter
import com.lckiss.pagecenter.presenter.contarct.AppInfoContract
import com.lckiss.pagecenter.ui.activity.AppDetailActivity
import com.lckiss.provider.ui.adapter.AppInfoAdapter
import com.lckiss.provider.ui.widget.DividerItemDecoration
import com.lckiss.provider.data.protocol.AppInfo
import kotlinx.android.synthetic.main.template_recycle_view.*
import org.jetbrains.anko.support.v4.startActivity
import java.io.Serializable

/**
 * 为推荐 排行 分类 抽象的BaseAppInfoFragment
 */
abstract class BaseAppInfoFragment : ProgressFragment<AppInfoPresenter>(), AppInfoContract.AppInfoView, BaseQuickAdapter.RequestLoadMoreListener {

    lateinit var mAppInfoAdapter: AppInfoAdapter

    var pageNum=0

    override fun init() {
        mPresenter.requestData(setType(), pageNum)

        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        val itemDecoration = DividerItemDecoration(activity as Context, DividerItemDecoration.VERTICAL_LIST)
        mRecyclerView.addItemDecoration(itemDecoration)

        mAppInfoAdapter = buildAdapter()
        mAppInfoAdapter.setOnLoadMoreListener(this, mRecyclerView)
        mRecyclerView.adapter = mAppInfoAdapter

        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                val item=mAppInfoAdapter.getItem(position) as Serializable
                startActivity<AppDetailActivity>("appinfo" to  item)
                mApplication.cacheView = view
            }
        })

    }

    override fun setLayout(): Int {
        return R.layout.template_recycle_view
    }

    /**
     * dagger绑定
     */
    override fun injectComponent(appComponent: AppComponent) {
        DaggerAppInfoComponent.builder().appComponent(appComponent)
                .appInfoModule(AppInfoModule(this)).build()
                .inject(this)
    }

    /**
     * 获取数据后的回调
     */
    override fun showResult(page: Page<AppInfo>) {
        //热门与普通数据格式不同
        if (page.datas!=null){
            mAppInfoAdapter.addData(page.datas)
        }else{
            mAppInfoAdapter.addData(page.listApp)
        }
        if (page.hasMore) {
            pageNum++
        }
        mAppInfoAdapter.setEnableLoadMore(page.hasMore)
    }

    /**
     * 加载更多完成
     */
    override fun onLoadMoreComplete() {
        mAppInfoAdapter.loadMoreComplete()
    }

    /**
     * 请求加载更多
     */
    override fun onLoadMoreRequested() {
        mPresenter.requestData(setType(), pageNum)
    }

    /**
     * 让子类提供不同type
     */
    abstract fun setType(): Int

    /**
     * 让子类提供不同的adapter
     */
    abstract fun buildAdapter(): AppInfoAdapter

    override fun getOnclickListener(): View.OnClickListener {
        return View.OnClickListener{
            mPresenter.requestData(setType(), pageNum)
        }
    }
}