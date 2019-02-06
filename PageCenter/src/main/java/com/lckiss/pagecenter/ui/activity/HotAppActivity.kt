package com.lckiss.pagecenter.ui.activity

import android.support.v4.content.ContextCompat
import android.view.View
import com.lckiss.baselib.common.BaseConstant.Companion.CATEGORY
import com.lckiss.baselib.common.BaseConstant.Companion.HOT_TYPE
import com.lckiss.baselib.presenter.BasePresenter
import com.lckiss.baselib.ui.activity.BaseActivity
import com.lckiss.pagecenter.R
import com.lckiss.pagecenter.data.protocol.Category
import com.lckiss.pagecenter.presenter.AppInfoPresenter
import com.lckiss.pagecenter.ui.adapter.CategoryAppViewPagerAdapter
import com.lckiss.pagecenter.ui.fragment.HotAppFragment
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.ionicons_typeface_library.Ionicons
import kotlinx.android.synthetic.main.activity_cateogry_app.*

/**
 * HotAppActivity定义
 */
class HotAppActivity : BaseActivity<BasePresenter<*, *>>() {

    //空实现
    override fun injectComponent() {
    }

    override fun init() {

        mToolbar.navigationIcon = IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_ios_arrow_back)
                .sizeDp(16)
                .color(ContextCompat.getColor(this,R.color.md_white_1000)
                )

        mToolbar.setNavigationOnClickListener{ this.finish() }

        val type = intent.getIntExtra(HOT_TYPE, 0);
        if (type == AppInfoPresenter.HOT_APP_LIST) {
            mToolbar.setTitle(R.string.hot_app)
        } else {
            mToolbar.setTitle(R.string.hot_game)
        }

        val fragment = HotAppFragment.newInstance(type)
        supportFragmentManager.beginTransaction().add(R.id.content_layout, fragment).commit()
    }

    override fun setLayout(): Int {
        return R.layout.activity_hot_app
    }
}