package com.lckiss.baselib.router

/**
 * ARouter路由
 */
object RouterPath {
    //用户模块
    class UserCenter{
        companion object {
            const val PATH_LOGIN = "/userCenter/login"
        }
    }

    class PageCenter{
        companion object {
            const val PATH_DETAIL = "/pageCenter/appDetail"
        }
    }

}