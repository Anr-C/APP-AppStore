package com.lckiss.appcenter.ui.fragment

import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.lckiss.appcenter.R
import com.lckiss.baselib.common.BaseConstant
import com.lckiss.baselib.common.execute
import com.lckiss.baselib.rx.observer.ProgressObserver
import com.lckiss.baselib.utils.ACache
import com.lckiss.provider.data.protocol.AppInfo
import com.lckiss.provider.ui.adapter.AppInfoAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.template_recycle_view.*

/**
 * 升级APP界面
 */
class UpgradeAppFragment : AppManagerFragment() {
    private lateinit var mAppInfoAdapter: AppInfoAdapter
    private lateinit var updateApps:List<AppInfo>

    override fun init() {
        super.init()
        mPresenter.getUpdateApps()
    }

    override fun setAdapter(): RecyclerView.Adapter<*> {
        mAppInfoAdapter = AppInfoAdapter.builder().updateStatus(true).retrofit(mRetrofit).showPosition(false).showBrief(true).build()
        mAppInfoAdapter.setEmptyView(R.layout.empty_view_vertical, mRecyclerView)
        mAppInfoAdapter.emptyView.setOnClickListener {
            mPresenter.getUpdateApps()
        }
        return mAppInfoAdapter
    }

    override fun showUpdateApps(appInfos: List<AppInfo>) {
        updateApps=appInfos
        mAppInfoAdapter.setNewData(appInfos)
    }

    /**
     * 应用状态改变时响应事件 这里是取消升级状态显示
     * @param packageName 包名
     */
    @Subscribe(tags = [(Tag(BaseConstant.Companion.EventType.TAG_APP_CHANGED))])
    fun refreshItem(packageName: String) {
        var position=-1
        for (i in updateApps.indices){
            if(updateApps[i].packageName==packageName){
                position=i
            }
        }
        if(position!=-1) {
            mAppInfoAdapter.remove(position)
        }
    }


}