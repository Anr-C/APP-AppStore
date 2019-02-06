package com.lckiss.appstore.ui.activity

import android.support.v4.content.ContextCompat
import android.view.View
import com.lckiss.appstore.R
import com.lckiss.appstore.ui.fragment.SettingFragment
import com.lckiss.baselib.presenter.BasePresenter
import com.lckiss.baselib.ui.activity.BaseActivity
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.ionicons_typeface_library.Ionicons
import kotlinx.android.synthetic.main.setting_framelayout.*

/**
 * 设置界面
 */
class SettingActivity: BaseActivity<BasePresenter<*, *>>() {
    override fun injectComponent() {

    }

    override fun init() {

        mToolbar.navigationIcon = IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_ios_arrow_back)
                .sizeDp(16)
                .color(ContextCompat.getColor(this,R.color.md_white_1000)
                )
        mToolbar.setTitle(R.string.sys_setting)

        mToolbar.setNavigationOnClickListener{ finish() }

        fragmentManager.beginTransaction().replace(R.id.content_view, SettingFragment()).commitAllowingStateLoss()
    }

    override fun setLayout(): Int {
     return R.layout.setting_framelayout
    }
}