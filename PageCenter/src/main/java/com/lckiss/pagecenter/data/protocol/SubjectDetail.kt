package com.lckiss.pagecenter.data.protocol

import com.lckiss.provider.data.protocol.AppInfo
import java.io.Serializable

/**
 * 主题简介
 */
class SubjectDetail:Serializable {

    /**
     * hasMore : true
     * host : http://f1.market.xiaomi.com/download/
     * listApp : []
     * thumbnail : http://t1.market.xiaomi.com/thumbnail/
     */

     var hasMore: Boolean = false
     var host: String? = null
     var thumbnail: String? = null
    lateinit var listApp: List<AppInfo>
     var description: String? = null
     var phoneBigIcon: String? = null

}