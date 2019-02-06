package com.lckiss.pagecenter.ui.fragment

import com.lckiss.pagecenter.presenter.AppInfoPresenter
import com.lckiss.provider.ui.adapter.AppInfoAdapter

/**
 * 排行榜
 */
class TopListFragment : BaseAppInfoFragment() {

    override fun buildAdapter(): AppInfoAdapter {
        return AppInfoAdapter.builder().showPosition(true).retrofit(mRetrofit)
                .showCategoryName(false).showBrief(true).build()
    }

    override fun setType(): Int {
        return AppInfoPresenter.TOP_LIST
    }

}
