package com.lckiss.baselib.common

/**
 *  基础常量
 */
class BaseConstant {
    companion object {

        /*Retrofit基础地址*/
        const val BASE_URL = "http://112.124.22.238:8081/course_api/cniaoplay/"

        /*响应成功*/
        const val SUCCESS = 1
        /*API错误*/
        const val API_ERROR = 0x0
        /*网络错误*/
        const val NETWORD_ERROR = 0x1
        /*http_错误*/
        const val HTTP_ERROR = 0x2
        /*json错误*/
        const val JSON_ERROR = 0x3
        /*未知错误*/
        const val UNKNOWN_ERROR = 0x4
        /*运行时异常-包含自定义异常*/
        const val RUNTIME_ERROR = 0x5
        /*无法解析该域名*/
        const val UNKOWNHOST_ERROR = 0x6
        /*连接网络超时*/
        const val SOCKET_TIMEOUT_ERROR = 0x7
        /*无网络连接*/
        const val SOCKET_ERROR = 0x8

        //api /////////////////////////////////////////

        // 服务器错误
        const val ERROR_API_SYSTEM = 10000

        // 登录错误，用户名密码错误
        const val ERROR_API_LOGIN = 10001

        //调用无权限的API
        const val ERROR_API_NO_PERMISSION = 10002

        //账户被冻结
        const val ERROR_API_ACCOUNT_FREEZE = 10003

        //TOKEN丢失
        const val LOST_TOKEN = 10010

        //TOKEN失效
        const val ERROR_TOKEN = 10011

        // http
        const val ERROR_HTTP_400 = 400

        const val ERROR_HTTP_404 = 404

        const val ERROR_HTTP_405 = 405

        const val ERROR_HTTP_500 = 500

        //app /////////////////////////////////////////

        const val BASE_IMG_URL = "http://file.market.xiaomi.com/mfc/thumbnail/png/w150q80/"

        const val IS_SHOW_GUIDE = "is_show_guide"

        const val HOT_TYPE = "hot_type"

        const val MODEL = "model"
        const val IMEI = "imei"
        const val LANGUAGE = "la"
        const val OS = "os"
        const val RESOLUTION = "resolution"
        const val SDK = "sdk"
        const val DENSITY_SCALE_FACTOR = "densityScaleFactor"
        const val PARAM = "p"
        const val TOKEN = "token"
        const val USER = "user"
        const val CATEGORY = "category"

        const val APK_DOWNLOAD_DIR = "apk_download_dir"
        const val APP_UPDATE_LIST = "app_update_list"
        const val POSITION = "position"

        const val TAG = "loginfo"

        object EventType {
            const val TAG_LOGIN = "tag_login"
            const val TAG_REFRESH = "tag_refresh"
            const val TAG_APP_CHANGED = "tag_app_changed"
            const val TAG_APP_REMOVEED = "tag_app_removeed"
        }

    }

}
