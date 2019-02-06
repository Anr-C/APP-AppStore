package com.lckiss.appcenter.ui.fragment

import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.View
import com.lckiss.appcenter.R
import com.lckiss.provider.data.protocol.Apk
import com.lckiss.appcenter.ui.adapter.ApkAdapter
import kotlinx.android.synthetic.main.template_recycle_view.*

/**
 * 已安装
 */
class InstalledFragment:AppManagerFragment() {

    private lateinit var mApkAdapter: ApkAdapter

    private lateinit var androidApks: List<Apk>

    /**
     * 初始化操作
     */
    override fun init() {
        super.init()
        mPresenter.initReceiver(Handler{msg->
            when(msg.what){
                0->{
                    //卸载成功 删除对应条目
                    val position=mPresenter.getPosition(androidApks,msg.obj as String)
                    if (position!=-1){
                        mApkAdapter.remove(position)
                    }
                }
                1->{
                    //安装成功 刷新已安装列表
                    mPresenter.getInstalledApps()
                }else->{}
            }
            false
        })
        mPresenter.getInstalledApps()
    }

    override fun setAdapter(): RecyclerView.Adapter<*> {
        mApkAdapter=ApkAdapter(ApkAdapter.FLAG_APP)
        mApkAdapter.setEmptyView(R.layout.empty_view_vertical, mRecyclerView)
        mApkAdapter.emptyView.setOnClickListener {
            mPresenter.getInstalledApps()
        }
        return mApkAdapter
    }

    override fun showApps(apps: List<Apk>) {
        androidApks=apps
        mApkAdapter.addData(apps)
    }

//    override fun getOnclickListener(): View.OnClickListener {
//        return View.OnClickListener {
//            mPresenter.getInstalledApps()
//        }
//    }

    /**
     * 销毁监听器
     */
    override fun onDestroy() {
        super.onDestroy()
        mPresenter.destroyReceiver()
    }
}