package com.lckiss.pagecenter.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.lckiss.pagecenter.ui.fragment.CategoryAppFragment
import java.util.ArrayList

/**
 * CategoryAppActivity的ViewPagerAdapter
 */
class CategoryAppViewPagerAdapter(fm: FragmentManager, private val categoryId: Int) : FragmentStatePagerAdapter(fm) {
    private val titles = ArrayList<String>(3)

    init {
        titles.add("精品")
        titles.add("排行")
        titles.add("新品")
    }

    override fun getItem(position: Int): Fragment {
        return CategoryAppFragment.newInstance(categoryId, position)
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}