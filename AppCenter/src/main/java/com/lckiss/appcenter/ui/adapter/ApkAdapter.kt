package com.lckiss.appcenter.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lckiss.appcenter.R
import com.lckiss.provider.data.protocol.Apk
import com.lckiss.baselib.common.execute
import com.lckiss.baselib.utils.AppUtils
import com.lckiss.baselib.utils.FileUtils
import com.lckiss.provider.ui.widget.DownloadProgressButton
import com.lckiss.provider.utils.chooseInstallType
import com.lckiss.provider.utils.chooseUnInstallType
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.template_apk.view.*

/**
 * 用于本地APK与APK卸载的适配器
 */
class ApkAdapter(val mFlag: Int) : BaseQuickAdapter<Apk, BaseViewHolder>(R.layout.template_apk) {
    companion object {
        const val FLAG_APK = 0
        const val FLAG_APP = 1
    }

    override fun convert(holder: BaseViewHolder, apk: Apk) {
        holder.itemView.mAppNameText.text = apk.appName
        holder.itemView.mAppIconImg.setImageDrawable(apk.mDrawable)
        holder.addOnClickListener(R.id.mDownloadBtn)

        val downloadBtn = holder.getView<DownloadProgressButton>(R.id.mDownloadBtn)
        if (mFlag == FLAG_APP) {
            downloadBtn.setText("卸载")
            downloadBtn.setOnClickListener {
                chooseUnInstallType(mContext, apk)
            }
            val statusText = mContext.resources.getString(R.string.app_status)
            val apkSize = AppUtils.byteToMB(AppUtils.getAppSize(downloadBtn.context, apk.packageName))
            val isSystem = if (apk.isSystem) "内置" else "第三方"
            holder.itemView.mStatusTv.text = String.format(statusText, apk.appVersionName, isSystem, apkSize)
        } else if (mFlag == FLAG_APK) {
            downloadBtn.setTag(R.id.tag_package_name, apk.packageName)
            downloadBtn.setOnClickListener {
                if (downloadBtn.getTag(R.id.tag_package_name) == apk.packageName) {
                    val isInstall = downloadBtn.tag
                    if (isInstall == null) {
                        chooseInstallType(mContext, apk.apkPath, null)

                    } else {
                        if (isInstall as Boolean) {
                            FileUtils.deleteFile(apk.apkPath)
                            remove(holder.adapterPosition)
                        } else {
                            chooseInstallType(mContext, apk.apkPath, null)
                        }
                    }
                }
            }

            Observable.create(ObservableOnSubscribe<Boolean> { e -> e.onNext(AppUtils.isInstalled(mContext, apk.packageName)) })
                    .execute().subscribe { isInstall ->
                        downloadBtn.tag = isInstall
                        if (isInstall) {
                            holder.itemView.mStatusTv.text = "V${apk.appVersionName} 已安装"
                            downloadBtn.setText("删除")
                        } else {
                            holder.itemView.mStatusTv.text = "V${apk.appVersionName} 等待安装"
                            downloadBtn.setText("安装")
                        }

                    }
        }
    }
}