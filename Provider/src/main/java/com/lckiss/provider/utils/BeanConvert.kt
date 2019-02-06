package com.lckiss.provider.utils

import com.lckiss.provider.data.protocol.AppInfo
import com.lckiss.baselib.data.protocol.DownloadMission
import com.lckiss.provider.data.protocol.AppDownloadInfo

/**
 * 提供AppInfo向下载任务DownloadMission的转化
 */
fun appInfoToMission(appInfo: AppInfo): DownloadMission {

    val url = if (appInfo.appDownloadInfo==null) "" else appInfo.appDownloadInfo!!.downloadUrl

    val mission = DownloadMission(url)

    mission.displayName=appInfo.displayName
    mission.id=appInfo.id
    mission.icon=appInfo.icon
    mission.saveName="${appInfo.displayName}_${appInfo.versionName}.apk"
    mission.releaseKeyHash=appInfo.releaseKeyHash
    mission.packageName=appInfo.packageName
    mission.tag=appInfo.packageName
    mission.versionName=appInfo.versionName

    return mission
}

/**
 * 提供下载任务mission向AppInfo的转化
 */
fun missionToAppInfo(mission: DownloadMission): AppInfo {
    val appInfo = AppInfo()
    appInfo.id=mission.id
    appInfo.icon=mission.icon
    appInfo.displayName=mission.displayName
    appInfo.packageName=mission.packageName
    appInfo.releaseKeyHash=mission.releaseKeyHash
    appInfo.versionName=mission.versionName
    val downloadInfo = AppDownloadInfo()
    downloadInfo.downloadUrl=mission.url
    appInfo.appDownloadInfo=downloadInfo
    return appInfo
}