package com.lckiss.pagecenter.ui.fragment

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.lckiss.baselib.common.BaseConstant
import com.lckiss.baselib.common.BaseConstant.Companion.CATEGORY
import com.lckiss.baselib.injection.component.AppComponent
import com.lckiss.baselib.ui.fragment.ProgressFragment
import com.lckiss.pagecenter.R
import com.lckiss.pagecenter.data.protocol.Category
import com.lckiss.pagecenter.injection.component.DaggerCategoryComponent
import com.lckiss.pagecenter.injection.module.CategoryModule
import com.lckiss.pagecenter.presenter.CategoryPresenter
import com.lckiss.pagecenter.presenter.contarct.CategoryContract
import com.lckiss.pagecenter.ui.activity.CategoryAppActivity
import com.lckiss.pagecenter.ui.adapter.CategoryAdapter
import com.lckiss.provider.ui.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.template_recycle_view.*
import org.jetbrains.anko.support.v4.startActivity
import java.io.Serializable

/**
 * 分类
 */
class CategoryFragment : ProgressFragment<CategoryPresenter>(), CategoryContract.CategoryView {

    private lateinit var mCategoryAdapter: CategoryAdapter

    override fun injectComponent(appComponent: AppComponent) {
        DaggerCategoryComponent.builder().categoryModule(CategoryModule(this))
                .appComponent(appComponent).build().inject(this)
    }

    override fun init() {
        mCategoryAdapter = CategoryAdapter()

        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        val itemDecoration = DividerItemDecoration(activity as Context, DividerItemDecoration.VERTICAL_LIST)
        mRecyclerView.addItemDecoration(itemDecoration)
        mRecyclerView.adapter = mCategoryAdapter
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                startActivity<CategoryAppActivity>(CATEGORY to mCategoryAdapter.getItem(position) as Serializable)
            }
        })
        mPresenter.getAllCategory()
    }

    override fun setLayout(): Int {
        return R.layout.template_recycle_view
    }

    override fun showData(categories: List<Category>) {
        mCategoryAdapter.setNewData(categories)
    }

    //RxBus事件处理
    @Subscribe(tags = arrayOf(Tag(BaseConstant.Companion.EventType.TAG_REFRESH)))
    fun updateCategory(flag: String) {
        mPresenter.getAllCategory()
    }

    override fun getOnclickListener(): View.OnClickListener {
        return View.OnClickListener{
            mPresenter.getAllCategory()
        }
    }
}