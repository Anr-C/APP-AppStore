package com.lckiss.provider.data.protocol

import java.io.Serializable

/**
 * APP详情
 */
class AppInfo : Serializable {
    var addTime: Int = 0
    var hasSameDevApp: Boolean = false
    var videoId: Int = 0
    var source: String = ""
    var versionName: String = ""
    lateinit var hdIcon: HdIconEntity
    var ratingScore: Float = 0f
    var briefShow: String = ""
    var developerId: Int = 0
    var fitness: Int = 0
    var level1CategoryId: Int = 0
    var releaseKeyHash: String=""
    var relateAppHasMore: Boolean = false
    var rId: Int = 0
    var suitableType: Int = 0
    var briefUseIntro: Boolean = false
    var ads: Int = 0
    var id: Int = 0
    var publisherName: String = ""
    var level2CategoryId: Int = 0
    var position: Int = 0
    //    var favorite: Boolean = false
    var isFavorite: Boolean = false
    var appendSize: Int = 0
    lateinit var relateAppInfoList: List<AppInfo>
    var level1CategoryName: String = ""
    var samDevAppHasMore: Boolean = false
    var displayName: String = ""
    var icon: String = ""
    var changeLog: String = ""
    var screenshot: String = ""
    var permissionIds: String = ""
    var ratingTotalCount: Int = 0
    var adType: Int = 0
    lateinit var web: String
    var apkSize: Int = 0
    var packageName: String = ""
    lateinit var appArticleInfoList: List<AppInfo>
    var introduction: String = ""
    var keyWords: String = ""
    var updateTime: Long = 0
    var grantCode: Int = 0
    var detailHeaderImage: String = ""
    lateinit var moduleInfoList: List<AppInfo>
    var versionCode: Int = 0
    lateinit var appTags: List<Tag>
    var diffFileSize: Int = 0
    lateinit var sameDevAppInfoList: List<AppInfo>
    var categoryId: String = ""
    var appDownloadInfo: AppDownloadInfo?=null

    inner class HdIconEntity : Serializable {
        /**
         * main : AppStore/01712d4cde311460f2415c0d2cbd6f37212d405fc
         */
        lateinit var main: String
    }

    inner class Tag : Serializable {
        /**
         * tagId : 107
         * link : sametag/107
         * tagName : 二手
         */
        var tagId: Int = 0
        lateinit var link: String
        lateinit var tagName: String
    }

}