package com.lckiss.appcenter.ui.fragment

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.lckiss.appcenter.R
import com.lckiss.provider.data.protocol.Apk
import com.lckiss.baselib.data.protocol.DownloadMission
import com.lckiss.appcenter.injection.component.DaggerAppManagerComponent
import com.lckiss.appcenter.injection.module.AppManagerModule
import com.lckiss.appcenter.presenter.AppManagerPresenter
import com.lckiss.appcenter.presenter.contract.AppManagerContract
import com.lckiss.baselib.injection.component.AppComponent
import com.lckiss.baselib.ui.fragment.ProgressFragment
import com.lckiss.provider.data.protocol.AppInfo
import com.lckiss.provider.ui.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.template_recycle_view.*

/**
 * AppManagerFragment
 */
abstract class AppManagerFragment : ProgressFragment<AppManagerPresenter>(), AppManagerContract.IAppManagerView {

    override fun injectComponent(appComponent: AppComponent) {
        DaggerAppManagerComponent.builder()
                .appComponent(appComponent)
                .appManagerModule(AppManagerModule(this))
                .build().inject(this)
    }

    override fun init() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        val itemDecoration = DividerItemDecoration(activity as Context, DividerItemDecoration.VERTICAL_LIST)
        mRecyclerView.addItemDecoration(itemDecoration)
        mRecyclerView.adapter = setAdapter()

    }

    override fun setLayout(): Int {
        return R.layout.template_recycle_view
    }

    override fun showDownload(downloadMissions: List<DownloadMission>) {
    }

    override fun showApps(apps: List<Apk>) {
    }

    override fun showUpdateApps(appInfos: List<AppInfo>) {
    }

    protected abstract fun setAdapter(): RecyclerView.Adapter<*>

    override fun getOnclickListener(): View.OnClickListener {
        return View.OnClickListener {
            //空实现 此处无网络数据回调
        }
    }

}