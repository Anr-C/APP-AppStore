package com.lckiss.pagecenter.ui.fragment

import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.lckiss.baselib.common.BaseConstant
import com.lckiss.pagecenter.presenter.AppInfoPresenter
import com.lckiss.provider.ui.adapter.AppInfoAdapter

/**
 * 游戏
 */
class GamesFragment : BaseAppInfoFragment() {

     override fun buildAdapter(): AppInfoAdapter {
        return AppInfoAdapter.builder().showBrief(true)
                .showCategoryName(false).showPosition(false).retrofit(mRetrofit).build()
    }

     override fun setType(): Int {
        return AppInfoPresenter.GAME
    }

    @Subscribe(tags = arrayOf(Tag(BaseConstant.Companion.EventType.TAG_REFRESH)))
    fun updateGame(flag: String) {
        mPresenter.requestData(AppInfoPresenter.GAME, 0)
    }
}