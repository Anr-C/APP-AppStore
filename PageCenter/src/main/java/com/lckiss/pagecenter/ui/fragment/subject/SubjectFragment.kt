package com.lckiss.pagecenter.ui.fragment.subject

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.hwangjr.rxbus.RxBus
import com.lckiss.pagecenter.R
import com.lckiss.pagecenter.data.protocol.Page
import com.lckiss.pagecenter.data.protocol.Subject
import com.lckiss.pagecenter.ui.adapter.SubjectAdapter
import com.lckiss.provider.ui.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.template_recycle_view.*

/**
 * SubjectFragment
 */
class SubjectFragment : BaseSubjectFragment(), BaseQuickAdapter.RequestLoadMoreListener {

    private var mPageNum = 0
    private lateinit var mSubjectAdapter: SubjectAdapter

    override fun init() {

        //布局
        val layoutManager = GridLayoutManager(activity, 2)
        mRecyclerView.layoutManager = layoutManager

        //间隔
        val dividerDecoration = SpaceItemDecoration(8)
        mRecyclerView.addItemDecoration(dividerDecoration)

        mSubjectAdapter = SubjectAdapter()
        mSubjectAdapter.setOnLoadMoreListener(this, mRecyclerView)
        mRecyclerView.adapter = mSubjectAdapter

        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                RxBus.get().post(adapter.getItem(position))
            }
        })

        //获取数据
        mPresenter.getSubjects(mPageNum)
    }

    override fun setLayout(): Int {
        return R.layout.template_recycle_view
    }

    override fun showSubjects(subjects: Page<Subject>) {
        mSubjectAdapter.addData(subjects.datas)
        if (subjects.hasMore) {
            mPageNum++
        }
        mSubjectAdapter.setEnableLoadMore(subjects.hasMore)
    }

    override fun getOnclickListener(): View.OnClickListener {
        return View.OnClickListener {
            mPresenter.getSubjects(mPageNum)
        }
    }

    override fun onLoadMoreRequested() {
        mPresenter.getSubjects(mPageNum)
    }
}