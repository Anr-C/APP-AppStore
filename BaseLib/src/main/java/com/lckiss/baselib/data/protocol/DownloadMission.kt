package com.lckiss.baselib.data.protocol

import zlc.season.rxdownload3.core.Mission

/**
 * 下载任务定义
 */
class DownloadMission : Mission {
    constructor(mission: Mission) : super(mission)
    constructor(url: String) : super(url)

    var id: Int = 0
    var icon: String=""
    var displayName: String=""
    var versionName: String=""
    var packageName: String=""
    var releaseKeyHash: String=""
    var status: Int = 0
    var needAutoStart: Int = 0
}