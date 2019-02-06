package com.lckiss.appcenter.ui.adapter

import android.os.Build
import android.widget.ImageButton
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hwangjr.rxbus.RxBus
import com.lckiss.appcenter.R
import com.lckiss.baselib.data.protocol.DownloadMission
import com.lckiss.baselib.common.BaseConstant
import com.lckiss.baselib.common.BaseConstant.Companion.BASE_IMG_URL
import com.lckiss.provider.ui.widget.DownloadButtonController
import com.lckiss.provider.ui.widget.DownloadProgressButton
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.ionicons_typeface_library.Ionicons
import retrofit2.Retrofit
import zlc.season.rxdownload3.RxDownload

/**
 * 处理下载任务
 */
class DownloadAdapter(mRetrofit: Retrofit) : BaseQuickAdapter<DownloadMission, BaseViewHolder>(R.layout.template_app_download_list) {

    private val downloadButtonController: DownloadButtonController = DownloadButtonController(mRetrofit)

    init {
        openLoadAnimation()
    }

    override fun convert(holder: BaseViewHolder, mission: DownloadMission) {
        //图标
        Glide.with(holder.itemView.context).load(BASE_IMG_URL + mission.icon).into(holder.getView(R.id.mAppIconImg))
        //文字
        holder.setText(R.id.mAppNameText, mission.displayName)

        //下载按钮处理
        val viewBtn = holder.getView<DownloadProgressButton>(R.id.mDownloadBtn)
        holder.addOnClickListener(R.id.mDownloadButton)
        downloadButtonController.handClick(viewBtn, mission)

        val viewDelBtn = holder.getView<ImageButton>(R.id.mDelMission)
        val background = IconicsDrawable(mContext, Ionicons.Icon.ion_ios_close_outline).colorRes(R.color.colorPrimary).sizeDp(17)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            viewDelBtn.setBackground(background)
        } else {
            viewDelBtn.setBackgroundDrawable(background)
        }

        viewDelBtn.setOnClickListener {
            RxDownload.delete(mission, true).subscribe()
            RxBus.get().post(BaseConstant.Companion.EventType.TAG_APP_REMOVEED, mission.packageName)
            remove(holder.layoutPosition)
        }
    }
}
