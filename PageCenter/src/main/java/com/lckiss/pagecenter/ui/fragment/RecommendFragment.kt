package com.lckiss.pagecenter.ui.fragment

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lckiss.baselib.injection.component.AppComponent
import com.lckiss.baselib.ui.fragment.ProgressFragment
import com.lckiss.pagecenter.R
import com.lckiss.pagecenter.data.protocol.Index
import com.lckiss.pagecenter.injection.component.DaggerRecommendComponent
import com.lckiss.pagecenter.injection.module.RecommendModule
import com.lckiss.pagecenter.presenter.RecommendPresenter
import com.lckiss.pagecenter.presenter.contarct.AppInfoContract
import com.lckiss.pagecenter.ui.adapter.IndexMutilAdapter
import kotlinx.android.synthetic.main.template_recycle_view.*
import org.jetbrains.anko.support.v4.toast

/**
 * 推荐页
 */
class RecommendFragment : ProgressFragment<RecommendPresenter>(), AppInfoContract.View {


    private lateinit var mIndexMutilAdapter: IndexMutilAdapter

    override fun injectComponent(appComponent: AppComponent) {
        DaggerRecommendComponent.builder()
                .appComponent(appComponent)
                .recommendModule(RecommendModule(this)).build().inject(this)
    }

    override fun init() {
        //布局管理器
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        //动画
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.HORIZONTAL))
        mPresenter.requestDatas()

    }

    override fun setLayout(): Int {
        return R.layout.template_recycle_view
    }

    override fun showResult(index: Index) {
        mIndexMutilAdapter=IndexMutilAdapter(activity as Context,mApplication,mRetrofit)
        mIndexMutilAdapter.setData(index)
        mRecyclerView.setAdapter(mIndexMutilAdapter)
    }

    override fun onRequestPermissionSuccess() {
        mPresenter.requestDatas()
    }

    override fun onRequestPermissionError() {
        toast("权限错误，请允许权限")
    }

    override fun getOnclickListener(): View.OnClickListener {
        return View.OnClickListener{
            mPresenter.requestDatas()

        }
    }

}

