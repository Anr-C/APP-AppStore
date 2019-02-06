package com.lckiss.appcenter.ui.fragment

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.lckiss.appcenter.R
import com.lckiss.baselib.data.protocol.DownloadMission
import com.lckiss.appcenter.ui.adapter.DownloadAdapter
import com.lckiss.baselib.router.RouterPath
import com.lckiss.provider.utils.missionToAppInfo
import kotlinx.android.synthetic.main.template_recycle_view.*
import java.io.Serializable

/**
 * 已下载 同下载中
 */
class DownloadedFragment :AppManagerFragment(){

    private lateinit var mDownloadAdapter: DownloadAdapter

    override fun init() {
        super.init()
        mPresenter.getDownloadedApps()

    }

    override fun setAdapter(): RecyclerView.Adapter<*> {
        mDownloadAdapter = DownloadAdapter(mRetrofit)
        mDownloadAdapter.setOnItemClickListener({ adapter, view, position ->
            //缓存View
            mApplication.cacheView = view
            //跳转
            ARouter.getInstance().build(RouterPath.PageCenter.PATH_DETAIL)
                    .withSerializable("appinfo", missionToAppInfo(adapter.getItem(position) as DownloadMission))
                    .navigation()
        })
        mDownloadAdapter.setEmptyView(R.layout.empty_view_vertical, mRecyclerView)
        mDownloadAdapter.emptyView.setOnClickListener {
            mPresenter.getDownloadedApps()
        }
        return mDownloadAdapter
    }

    override fun showDownload(downloadMissions: List<DownloadMission>) {
        mDownloadAdapter.setNewData(downloadMissions)
    }
}