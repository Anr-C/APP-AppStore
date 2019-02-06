package com.lckiss.appcenter.ui.fragment

import android.support.v7.widget.RecyclerView
import android.view.View
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.lckiss.appcenter.R
import com.lckiss.provider.data.protocol.Apk
import com.lckiss.appcenter.ui.adapter.ApkAdapter
import com.lckiss.baselib.common.BaseConstant
import kotlinx.android.synthetic.main.template_recycle_view.*

/**
 * 本地APK安装包
 */
class LocalApkFragment : AppManagerFragment() {

    private lateinit var mApkAdapter: ApkAdapter

    private lateinit var mApks: List<Apk>

    override fun init() {
        super.init()
        mPresenter.getLocalApks()
    }

    override fun setAdapter(): RecyclerView.Adapter<*> {
        mApkAdapter = ApkAdapter(ApkAdapter.FLAG_APK)
        mApkAdapter.setEmptyView(R.layout.empty_view_vertical, mRecyclerView)
//        mApkAdapter.emptyView.setOnClickListener {
//            mPresenter.getLocalApks()
//        }
        return mApkAdapter
    }

    override fun showApps(apps: List<Apk>) {
        mApks=apps
        mApkAdapter.addData(apps)
    }

    override fun getOnclickListener(): View.OnClickListener {
        return View.OnClickListener {
            mPresenter.getLocalApks()
        }
    }

    /**
     * 应用状态改变时响应事件
     * @param packageName 包名
     */
    @Subscribe(tags = [(Tag(BaseConstant.Companion.EventType.TAG_APP_CHANGED))])
    fun refreshItem(packageName: String) {
        val position = mPresenter.getPosition(mApks, packageName)
        mApkAdapter.setData(position, mApks[position])
    }


    /**
     * 当应用从已完成界面删除时的响应事件 此时安装包已经不存在 需要移除
     * @param packageName 包名
     */
    @Subscribe(tags = [(Tag(BaseConstant.Companion.EventType.TAG_APP_REMOVEED))])
    fun deleteItem(packageName: String) {
        val position = mPresenter.getPosition(mApks, packageName)
        mApkAdapter.remove(position)
    }

}