package com.lckiss.appcenter.ui.activity

import android.support.v4.content.ContextCompat
import android.view.View
import com.lckiss.appcenter.R
import com.lckiss.appcenter.ui.adapter.ViewPagerAdapter
import com.lckiss.appcenter.ui.fragment.*
import com.lckiss.baselib.common.BaseConstant.Companion.POSITION
import com.lckiss.baselib.presenter.BasePresenter
import com.lckiss.baselib.ui.activity.BaseActivity
import com.lckiss.provider.data.protocol.FragmentInfo
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.ionicons_typeface_library.Ionicons
import kotlinx.android.synthetic.main.activity_app_manager.*
import java.util.ArrayList

/**
 * 应用管理
 */
class AppManagerActivity : BaseActivity<BasePresenter<*, *>>() {

    //页面位置
    private var mTabPosition: Int = 0


    override fun injectComponent() {
    }

    override fun init() {
        mTabPosition = intent.getIntExtra(POSITION, 0)

        //ToolBar
        mToolbar.navigationIcon = IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_ios_arrow_back)
                .sizeDp(16)
                .color(ContextCompat.getColor(this, R.color.md_white_1000)
                )

        mToolbar.setNavigationOnClickListener(View.OnClickListener { finish() })

        mToolbar.setTitle(R.string.download_manager)

        //TabLayout ViewPager init
        val fragments = ArrayList<FragmentInfo>(5)
        fragments.add(FragmentInfo("下载中", DownloadingFragment::class.java))
        fragments.add(FragmentInfo("已完成", DownloadedFragment::class.java))
        fragments.add(FragmentInfo("待升级", UpgradeAppFragment::class.java))
        fragments.add(FragmentInfo("已安装", InstalledFragment::class.java))
        fragments.add(FragmentInfo("安装包", LocalApkFragment::class.java))

        val adapter = ViewPagerAdapter(supportFragmentManager, fragments)
        mViewPager.offscreenPageLimit = adapter.count
        mViewPager.adapter = adapter
        mTabLayout.setupWithViewPager(mViewPager)
        //选择位置
        mViewPager.currentItem = mTabPosition
        mTabLayout.getTabAt(mTabPosition)!!.select()
    }

    override fun setLayout(): Int {
        return R.layout.activity_app_manager
    }
}