package com.lckiss.baselib.service

import android.accessibilityservice.AccessibilityService
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

/**
 * 辅助安装功能
 */
class InstallAccessibilityService : AccessibilityService() {
    override fun onInterrupt() {
        //EMPTY
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val nodeInfo = event.source ?: return
        val evenType = event.eventType
        if (evenType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED || evenType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            // 中文系统
            click("继续")
            click("下一步")
            click("安装")
            click("完成")
            // 英文
            //...
        }
    }

    private fun click(text: String) {
        val rootNodeInfo: AccessibilityNodeInfo?

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            rootNodeInfo = rootInActiveWindow
        } else {
            try {
                throw Exception("InstallAccessibilityService: SDK ERROR")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return
        }

        val nodeInfos = rootNodeInfo.findAccessibilityNodeInfosByText(text) ?: return
        nodeInfos.map {
            if (it.className == "android.widget.Button" && it.isClickable) {
                //模拟用户点击
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }else{
                    try {
                        throw Exception("InstallAccessibilityService: SDK TOO LOW")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

}