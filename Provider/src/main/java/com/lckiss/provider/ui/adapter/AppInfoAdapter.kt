package com.lckiss.provider.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lckiss.baselib.common.BaseConstant.Companion.BASE_IMG_URL
import com.lckiss.provider.R
import com.lckiss.provider.data.protocol.AppInfo
import com.lckiss.provider.ui.widget.DownloadButtonController
import com.ms.square.android.expandabletextview.ExpandableTextView
import retrofit2.Retrofit

/**
 * 通用AppInfo适配器
 */
class AppInfoAdapter private constructor(builder: Builder) : BaseQuickAdapter<AppInfo, BaseViewHolder>(builder.resId) {

    private var mBuilder: Builder = builder
    private var downloadButtonController: DownloadButtonController

    init {
        openLoadAnimation()
        downloadButtonController = DownloadButtonController(builder.retrofit!!)
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }

        class Builder {

            var isShowPosition: Boolean = false
            var isShowCategoryName: Boolean = false
            var isShowBrief: Boolean = false
            var retrofit: Retrofit? = null
            var isUpdateStatus: Boolean = false
            var resId = R.layout.template_appinfo

            fun showPosition(b: Boolean): Builder {
                this.isShowPosition = b
                return this
            }

            fun showCategoryName(b: Boolean): Builder {
                this.isShowCategoryName = b
                return this
            }

            fun showBrief(b: Boolean): Builder {
                this.isShowBrief = b
                return this
            }

            fun layout(resId: Int): Builder {
                this.resId = resId
                return this
            }

            fun retrofit(retrofit: Retrofit): Builder {
                this.retrofit = retrofit
                return this
            }

            fun updateStatus(b: Boolean): Builder {
                this.isUpdateStatus = b
                return this
            }

            fun build(): AppInfoAdapter {
                return AppInfoAdapter(this)
            }
        }
    }

    override fun convert(holder: BaseViewHolder, appInfoItem: AppInfo) {
        val mRootView: View = holder.itemView

        mRootView.destroyDrawingCache()

        holder.addOnClickListener(R.id.mDownloadButton)

        //图标
        val mIconIv = mRootView.findViewById<ImageView>(R.id.mAppIconImg)
        Glide.with(mContext).load(BASE_IMG_URL + appInfoItem.icon).into(mIconIv)

        //文字
        val mAppNameTv = mRootView.findViewById<TextView>(R.id.mAppNameText)
        mAppNameTv.text = appInfoItem.displayName

        //排名
        val mPositionTv = mRootView.findViewById<TextView>(R.id.mPositionText)
        if (mPositionTv != null) {
            mPositionTv.visibility = if (mBuilder.isShowPosition) View.VISIBLE else View.GONE
            mPositionTv.text = "${appInfoItem.position + 1}."
        }

        //下载按钮
        val mDownloadBtn = mRootView.findViewById<com.lckiss.provider.ui.widget.DownloadProgressButton>(R.id.mDownloadButton)
        downloadButtonController.handClick(mDownloadBtn, appInfoItem)

        val mAppBriefTv = mRootView.findViewById<TextView>(R.id.mAppBriefText)
        if (mBuilder.isUpdateStatus) {
            mRootView.findViewById<ExpandableTextView>(R.id.mChangeLogView).text = appInfoItem.changeLog
            if (mAppBriefTv != null) {
                mAppBriefTv.visibility = View.VISIBLE
                mAppBriefTv.text = "v${appInfoItem.versionName} ${appInfoItem.apkSize / 1024 / 1024}MB"
            }
        } else {
            val mAppCategoryTv = mRootView.findViewById<TextView>(R.id.mAppCategoryText)

            if (mAppCategoryTv != null) {
                mAppCategoryTv.visibility = if (mBuilder.isShowCategoryName) View.VISIBLE else View.GONE
                mAppCategoryTv.text = appInfoItem.level1CategoryName
            }

            if (mAppBriefTv != null) {
                mAppBriefTv.visibility = if (mBuilder.isShowBrief) View.VISIBLE else View.GONE
                mAppBriefTv.text = appInfoItem.briefShow
            }

            val mAppApkSizeTv = mRootView.findViewById<TextView>(R.id.mAppApkSizeText)
            if (mAppApkSizeTv != null) {
                mAppApkSizeTv.text = "${appInfoItem.apkSize / 1014 / 1024}MB"
            }
        }
    }


}