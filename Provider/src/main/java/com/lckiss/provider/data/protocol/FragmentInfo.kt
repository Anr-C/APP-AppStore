package com.lckiss.provider.data.protocol

import android.support.v4.app.Fragment

/**
 * 首页Fragment封装
 */
data class  FragmentInfo (var title: String,var fragment:Class<out Fragment>)