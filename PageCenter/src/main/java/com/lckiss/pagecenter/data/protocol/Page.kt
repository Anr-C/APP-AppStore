package com.lckiss.pagecenter.data.protocol

/**
 * 分页数据
 */
data class Page<T>(var hasMore: Boolean,var status: Int,var message: String,var datas: List<T>,var listApp: List<T>)