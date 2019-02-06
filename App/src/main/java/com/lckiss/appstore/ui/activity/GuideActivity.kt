package com.lckiss.appstore.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.lckiss.appstore.R
import com.lckiss.appstore.ui.adapter.GuideFragmentAdapter
import com.lckiss.appstore.ui.fragment.GuideFragment
import com.lckiss.baselib.common.BaseConstant.Companion.IS_SHOW_GUIDE
import com.lckiss.baselib.utils.ACache
import kotlinx.android.synthetic.main.activity_guide.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList

/**
 * GuideActivity定义
 */
class GuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        //初始化fragments数据
        val fragments = ArrayList<Fragment>()
        fragments.add(GuideFragment.newInstance(R.drawable.guide_1, R.color.color_bg_guide1, R.string.guide_1))
        fragments.add(GuideFragment.newInstance(R.drawable.guide_2, R.color.color_bg_guide2, R.string.guide_2))
        fragments.add(GuideFragment.newInstance(R.drawable.guide_3, R.color.color_bg_guide3, R.string.guide_3))

        //初始化Viewpager
        val mGuideAdapter = GuideFragmentAdapter(supportFragmentManager, fragments)
        mViewpager.currentItem = 0
        mViewpager.adapter = mGuideAdapter
        mViewpager.offscreenPageLimit = mGuideAdapter.count

        mViewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //最后一页才显示按钮
                if (position == mGuideAdapter.count - 1) {
                    mEnterBtn.visibility = View.VISIBLE
                } else {
                    mEnterBtn.visibility = View.GONE
                }
            }

            override fun onPageSelected(position: Int) {
            }

        })

        //设置指标
        mCircleIndicator.setViewPager(mViewpager)

        //最后得点击事件
        mEnterBtn.setOnClickListener {
            ACache.get(this).put(IS_SHOW_GUIDE, "0")
            startActivity<MainActivity>()
        }
    }

}