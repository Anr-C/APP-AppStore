package com.lckiss.provider.data.protocol

import java.io.Serializable

/**
 * APP下载详情
 */
class AppDownloadInfo :Serializable {
    /**
     * thumbnail : http://t1.market.xiaomi.com/thumbnail/
     * releaseKeyHash : be910af39a26a4a992c6fd01a143ed19
     * icon : AppStore/072725ca573700292b92e636ec126f51ba4429a50
     * apkHash : 010d7c92640b2e994839a81589d83bfa
     * appendExpansionPackSize : 0
     * hdIcon : {"main":"AppStore/07750d40a68e2445a3439a8f781083c431bfa5934"}
     * mainExpansionPackSize : 0
     * channelApkId : -1
     * fitness : 0
     * gamePackSize : 0
     * host : http://f6.market.xiaomi.com/download/
     * diffFileSize : 0
     * apkSize : 40309436
     * id : 1359
     * apk : AppStore/07650c4f6a86443a03920b69d83268aec54f00f5d
     * refPosition : -1
     */
     var thumbnail: String = ""
     var releaseKeyHash: String = ""
     var icon: String = ""
     var apkHash: String = ""
     var appendExpansionPackSize: Int = 0
     var hdIcon: HdIconEntity ?= null
     var mainExpansionPackSize: Int = 0
     var channelApkId: Int = 0
     var fitness: Int = 0
     var gamePackSize: Int = 0
     var host: String = ""
     var diffFileSize: Int = 0
     var apkSize: Int = 0
     var id: Int = 0
     var apk: String = ""
     var refPosition: Int = 0
     var downloadUrl: String = ""
        get() {
           return this.host+this.apk
        }

    inner class HdIconEntity : Serializable {
        /**
         * main : AppStore/07750d40a68e2445a3439a8f781083c431bfa5934
         */
        var main: String = ""
    }
}