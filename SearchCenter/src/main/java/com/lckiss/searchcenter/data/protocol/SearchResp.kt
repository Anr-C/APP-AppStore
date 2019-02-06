package com.lckiss.searchcenter.data.protocol

import com.lckiss.provider.data.protocol.AppInfo
import java.io.Serializable

/**
 * 搜索结果
 */
data class SearchResp(var isHasMore: Boolean,var listApp: List<AppInfo>) : Serializable
