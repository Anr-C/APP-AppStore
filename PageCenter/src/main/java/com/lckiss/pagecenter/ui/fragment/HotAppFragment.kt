package com.lckiss.pagecenter.ui.fragment

import android.os.Bundle
import com.lckiss.pagecenter.presenter.AppInfoPresenter
import com.lckiss.provider.ui.adapter.AppInfoAdapter

/**
 * 首页热门应用/游戏
 */
class HotAppFragment:BaseAppInfoFragment() {

    companion object {
        fun newInstance(type: Int): HotAppFragment {
            val fragment = HotAppFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setType(): Int {
       if(arguments!!.getInt("type")== AppInfoPresenter.HOT_APP_LIST){
           return AppInfoPresenter.HOT_APP_LIST
       }else{
           return AppInfoPresenter.GAME
       }
    }

    override fun buildAdapter(): AppInfoAdapter {
        return AppInfoAdapter.builder().showPosition(true).showBrief(false).showCategoryName(true).retrofit(mRetrofit).build()
    }
}