package com.lckiss.baselib.presenter.view

/**
 * BaseView定义
 */
interface BaseView {
    /**
     * 显示加载提示
     */
    fun showLoading()

    /**
     * 显示错误
     */
    fun showError(msg:String)

    /**
     * 隐藏加载提示框
     */
    fun dismissLoading()
}