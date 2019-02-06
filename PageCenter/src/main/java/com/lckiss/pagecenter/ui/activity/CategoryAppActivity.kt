package com.lckiss.pagecenter.ui.activity

import android.support.v4.content.ContextCompat
import android.view.View
import com.lckiss.baselib.common.BaseConstant.Companion.CATEGORY
import com.lckiss.baselib.presenter.BasePresenter
import com.lckiss.baselib.ui.activity.BaseActivity
import com.lckiss.pagecenter.R
import com.lckiss.pagecenter.data.protocol.Category
import com.lckiss.pagecenter.ui.adapter.CategoryAppViewPagerAdapter
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.ionicons_typeface_library.Ionicons
import kotlinx.android.synthetic.main.activity_cateogry_app.*

/**
 * CategoryAppActivity定义
 */
class CategoryAppActivity : BaseActivity<BasePresenter<*, *>>() {

    //空实现
    override fun injectComponent() {
    }

    override fun init() {
        val category = intent.getSerializableExtra(CATEGORY) as Category

        mToolbar.navigationIcon = IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_ios_arrow_back)
                .sizeDp(16)
                .color(ContextCompat.getColor(this,R.color.md_white_1000)
                )

        mToolbar.setNavigationOnClickListener{ this.finish() }

        val adapter = CategoryAppViewPagerAdapter(supportFragmentManager, category.id)

        mViewPager.offscreenPageLimit = adapter.count
        mViewPager.adapter = adapter
        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun setLayout(): Int {
        return R.layout.activity_cateogry_app
    }
}