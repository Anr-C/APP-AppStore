package com.lckiss.pagecenter.ui.fragment.subject

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.lckiss.baselib.common.BaseConstant.Companion.BASE_IMG_URL
import com.lckiss.pagecenter.R
import com.lckiss.pagecenter.data.protocol.Subject
import com.lckiss.pagecenter.data.protocol.SubjectDetail
import com.lckiss.pagecenter.ui.activity.AppDetailActivity
import com.lckiss.provider.ui.adapter.AppInfoAdapter
import com.lckiss.provider.ui.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_subject_detail.*
import org.jetbrains.anko.startActivity
/**
 * SubjectDetailFragment
 */
class SubjectDetailFragment : BaseSubjectFragment() {

    private lateinit var mAppInfoAdapter: AppInfoAdapter
    private lateinit var mSubject: Subject

    companion object {
        fun newInstance(subject: Subject): SubjectDetailFragment {
            val fragment = SubjectDetailFragment()
            val bundle = Bundle()
            bundle.putSerializable("subject", subject)
            fragment.arguments = bundle;
            return fragment
        }
    }


    override fun init() {
        //初始化布局
        mAppInfoAdapter = AppInfoAdapter.builder().showBrief(false).showCategoryName(true).retrofit(mRetrofit)
                .build()
        val layoutManager = LinearLayoutManager(activity)
        mRecyclerView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity as Context, DividerItemDecoration.VERTICAL_LIST)
        mRecyclerView.addItemDecoration(itemDecoration)

        mRecyclerView.adapter = mAppInfoAdapter

        mAppInfoAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            mApplication.cacheView=view
            context!!.startActivity<AppDetailActivity>("appinfo" to adapter.getItem(position))
        }

        //获取数据
        mSubject = arguments!!.getSerializable("subject") as Subject
        mPresenter.getSubjectDetail(mSubject.relatedId)

    }

    /**
     * 设置布局资源ID
     */
    override fun setLayout(): Int {
        return R.layout.fragment_subject_detail
    }

    /**
     * 设置返回的数据
     */
    override fun showSubjectDetail(detail: SubjectDetail) {
        Glide.with(mImageView.context).load(BASE_IMG_URL + detail.phoneBigIcon).into(mImageView)
        mDescTv.text = detail.description
        mAppInfoAdapter.addData(detail.listApp)
    }

    /**
     * 加载失败后的点击事件
     */
    override fun getOnclickListener(): View.OnClickListener {
        return View.OnClickListener {
            mPresenter.getSubjectDetail(mSubject.relatedId)
        }
    }
}