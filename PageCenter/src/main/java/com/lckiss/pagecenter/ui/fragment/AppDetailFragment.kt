package com.lckiss.pagecenter.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lckiss.baselib.common.BaseConstant.Companion.BASE_IMG_URL
import com.lckiss.baselib.injection.component.AppComponent
import com.lckiss.baselib.ui.fragment.ProgressFragment
import com.lckiss.baselib.utils.DateUtils
import com.lckiss.pagecenter.R
import com.lckiss.pagecenter.injection.component.DaggerAppDetailComponent
import com.lckiss.pagecenter.injection.module.AppDetailModule
import com.lckiss.pagecenter.injection.module.AppModelModule
import com.lckiss.pagecenter.presenter.AppDetailPresenter
import com.lckiss.pagecenter.presenter.contarct.AppInfoContract
import com.lckiss.provider.ui.adapter.AppInfoAdapter
import com.lckiss.provider.data.protocol.AppInfo
import kotlinx.android.synthetic.main.fragment_app_detail.*

/**
 * 详情页面Fragment
 */
class AppDetailFragment : ProgressFragment<AppDetailPresenter>(), AppInfoContract.AppDetailView {

    private lateinit var mInflater: LayoutInflater

    private lateinit var mAdapter: AppInfoAdapter

    private var mAppId: Int = 0

    companion object {
        fun newInstance(id: Int): AppDetailFragment {
            val fragment = AppDetailFragment()
            val bundle = Bundle()
            bundle.putInt("appId", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun injectComponent(appComponent: AppComponent) {
        DaggerAppDetailComponent.builder().appComponent(appComponent)
                .appDetailModule(AppDetailModule(this))
                .appModelModule(AppModelModule())
                .build().inject(this)
    }

    override fun init() {
        mInflater = LayoutInflater.from(activity)

        mAppId = arguments!!.getInt("appId")

        mPresenter.getAppDetail(mAppId)

    }

    override fun setLayout(): Int {
        return R.layout.fragment_app_detail
    }

    override fun showAppDetail(appInfo: AppInfo) {

        //应用截图画廊
        val urls = appInfo.screenshot.split(",")
        for (url in urls) {
            val mPerGalleryView = mInflater.inflate(R.layout.template_imageview_detail, mGalleryView, false) as ImageView
            Glide.with(this).load(BASE_IMG_URL + url).into(mPerGalleryView)
            mGalleryView.addView(mPerGalleryView)
        }

        //应用数据
        mIntroductionView.text = appInfo.introduction

        mUpdateTimeTv.text = DateUtils.formatDate(appInfo.updateTime)
        ApkSizeTv.text = "${appInfo.apkSize / 1014 / 1024} Mb"

        mVersionTv.text = appInfo.versionName
        mPublisherTv.text = appInfo.publisherName
        mPerPublisher.text = appInfo.publisherName

        //同作者应用Rv

        mAdapter = AppInfoAdapter.builder().layout(R.layout.template_appinfo_small).retrofit(mRetrofit)
                .build()

        mSameDeveloperRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        mAdapter.setEmptyView(R.layout.empty_view_horizontal, mSameDeveloperRv)

        mAdapter.addData(appInfo.sameDevAppInfoList)

        mSameDeveloperRv.adapter = mAdapter

        //相似应用Rv

        mAdapter = AppInfoAdapter.builder().layout(R.layout.template_appinfo_small).retrofit(mRetrofit)
                .build()
        mRelateRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        mAdapter.addData(appInfo.relateAppInfoList)

        mAdapter.setEmptyView(R.layout.empty_view_horizontal, mRelateRv)
        mRelateRv.adapter = mAdapter

    }

    override fun getOnclickListener(): View.OnClickListener {
        return View.OnClickListener {
            mPresenter.getAppDetail(mAppId)
        }
    }
}