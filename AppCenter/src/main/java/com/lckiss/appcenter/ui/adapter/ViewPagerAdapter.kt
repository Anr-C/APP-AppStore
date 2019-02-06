package com.lckiss.appcenter.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.lckiss.provider.data.protocol.FragmentInfo

/**
 * ViewPagerAdapter
 */
class ViewPagerAdapter(fm: FragmentManager, val fragments: List<FragmentInfo>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        try {
            return fragments.get(position).fragment.newInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItemPosition(any: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragments.get(position).title
    }

}