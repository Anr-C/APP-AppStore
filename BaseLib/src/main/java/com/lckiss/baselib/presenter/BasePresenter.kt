package com.lckiss.baselib.presenter

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.lckiss.baselib.presenter.view.BaseView

/**
 * BasePresenter定义
 */
open class BasePresenter<M,V:BaseView> {

    var mModel:M ?=null
   lateinit var mView: V


    constructor()
    constructor(mModel:M, mView: V){
        this.mModel=mModel
        this.mView=mView
        initContext()
    }

    lateinit var mContext:Context

    private fun initContext() {
        if (mView is Fragment){
            mContext=(mView as Fragment).activity as Context
        }else{
            mContext=mView as Activity
        }
    }

}