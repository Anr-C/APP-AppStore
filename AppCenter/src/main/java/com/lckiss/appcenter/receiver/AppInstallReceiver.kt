package com.lckiss.appcenter.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Message
import com.hwangjr.rxbus.RxBus
import com.lckiss.baselib.common.BaseConstant

/**
 * 监听应用安装 卸载变化
 */
class AppInstallReceiver(private var handler: Handler) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val packageName = intent.data.schemeSpecificPart
        val message = Message()
        message.obj = packageName
        if (intent.action == Intent.ACTION_PACKAGE_ADDED) {
            message.what = 1
        }
        if (intent.action == Intent.ACTION_PACKAGE_REMOVED) {
            message.what = 0
        }
        handler.sendMessage(message)
        //发送给MainActivity更新角标 并且更新需要更新的app列表 以及刷新本地apk状态
        RxBus.get().post(BaseConstant.Companion.EventType.TAG_APP_CHANGED, packageName)
    }

}