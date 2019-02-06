package com.lckiss.pagecenter.data.protocol

import com.lckiss.provider.data.protocol.AppInfo


/**
 * 首页
 */
data class Index(val banners: List<Banner>, val recommendApps: List<AppInfo>, val recommendGames: List<AppInfo>)