package com.lckiss.appcenter.ui.fragment

import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.lckiss.appcenter.R
import com.lckiss.baselib.data.protocol.DownloadMission
import com.lckiss.appcenter.ui.adapter.DownloadAdapter
import com.lckiss.baselib.router.RouterPath
import kotlinx.android.synthetic.main.template_recycle_view.*
import java.io.Serializable

/**
 * 正在下载中的任务
 */
class DownloadingFragment:AppManagerFragment() {

    private lateinit var mDownloadAdapter: DownloadAdapter

    override fun init() {
        super.init()
        mPresenter.getDownloadingApps()
    }

    /**
     * 设置适配器
     */
    override fun setAdapter(): RecyclerView.Adapter<*> {
        mDownloadAdapter = DownloadAdapter(mRetrofit)
        mDownloadAdapter.setOnItemClickListener{ adapter, view, position ->
            //缓存View
            mApplication.cacheView = view
            //跳转
            ARouter.getInstance().build(RouterPath.PageCenter.PATH_DETAIL)
                    .withSerializable("appinfo", adapter.getItem(position) as Serializable)
                    .navigation()
        }
        mDownloadAdapter.setEmptyView(R.layout.empty_view_vertical, mRecyclerView)
        mDownloadAdapter.emptyView.setOnClickListener {
            mPresenter.getDownloadingApps()
        }
        return mDownloadAdapter
    }

    /**
     * 获取下载中得任务后得回调
     */
    override fun showDownload(downloadMissions: List<DownloadMission>) {
        mDownloadAdapter.setNewData(downloadMissions)
    }
}