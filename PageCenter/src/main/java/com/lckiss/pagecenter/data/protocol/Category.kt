package com.lckiss.pagecenter.data.protocol

import java.io.Serializable

/**
 * Category
 */

class Category : Serializable {

    /**
     * setName : true
     * addTime : 0
     * setAddTime : false
     * setIcon192 : false
     * topAppId : 48217
     * icon : AppStore/816d41df-4a7a-40a5-84ff-0ff536b6cbbf
     * iconForPad :
     * setDescription : false
     * hdIcon : {}
     * categoryenum : 1
     * setTopAppIdForPad : true
     * setIcon136 : false
     * hdIconSize : 0
     * setId : true
     * setOperator : false
     * id : 15
     * setTopAppId : true
     * topAppIdForPad : 49776
     * setCategoryenum : true
     * setIconForPad : true
     * updateTime : 1398650464153
     * priority : 10
     * parentId : 0
     * setStatus : false
     * appQuantity : 0
     * setIcon : true
     * setAppQuantity : false
     * setIcon224 : false
     * setIcon90 : false
     * setIcon168 : false
     * name : 游戏
     * setParentId : true
     * setUpdateTime : true
     * setPriority : true
     * setHdIcon : true
     * status : 0
     */
    var setName: Boolean = false
    var addTime: Int = 0
    var setAddTime: Boolean = false
    var setIcon192: Boolean = false
    var topAppId: Int = 0
    var icon: String? = null
    var iconForPad: String? = null
    var setDescription: Boolean = false
    private var hdIcon: HdIconEntity? = null
    var categoryenum: Int = 0
    var setTopAppIdForPad: Boolean = false
    var setIcon136: Boolean = false
    var hdIconSize: Int = 0
    var setId: Boolean = false
    var setOperator: Boolean = false
    var id: Int = 0
    var setTopAppId: Boolean = false
    var topAppIdForPad: Int = 0
    var setCategoryenum: Boolean = false
    var setIconForPad: Boolean = false
    var updateTime: Long = 0
    var priority: Int = 0
    var parentId: Int = 0
    var setStatus: Boolean = false
    var appQuantity: Int = 0
    var setIcon: Boolean = false
    var setAppQuantity: Boolean = false
    var setIcon224: Boolean = false
    var setIcon90: Boolean = false
    var setIcon168: Boolean = false
    var name: String? = null
    var setParentId: Boolean = false
    var setUpdateTime: Boolean = false
    var setPriority: Boolean = false
    var setHdIcon: Boolean = false
    var status: Int = 0

    internal inner class HdIconEntity : Serializable
}