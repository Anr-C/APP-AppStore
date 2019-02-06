package com.lckiss.baselib.data.net

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.lckiss.baselib.common.BaseConstant.Companion.DENSITY_SCALE_FACTOR
import com.lckiss.baselib.common.BaseConstant.Companion.IMEI
import com.lckiss.baselib.common.BaseConstant.Companion.LANGUAGE
import com.lckiss.baselib.common.BaseConstant.Companion.MODEL
import com.lckiss.baselib.common.BaseConstant.Companion.OS
import com.lckiss.baselib.common.BaseConstant.Companion.PARAM
import com.lckiss.baselib.common.BaseConstant.Companion.RESOLUTION
import com.lckiss.baselib.common.BaseConstant.Companion.SDK
import com.lckiss.baselib.common.BaseConstant.Companion.TOKEN
import com.lckiss.baselib.utils.ACache
import com.lckiss.baselib.utils.DensityUtil
import com.lckiss.baselib.utils.DeviceUtils
import okhttp3.*
import okio.Buffer

/**
 * 公共参数拦截器
 */
class CommonParamsInterceptor(val gson: Gson, val context: Context) : Interceptor {

    companion object {
        private const val TAG = "CommonParamsInterceptor"
        val JSON = MediaType.parse("application/json;charset=utf-8")
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val method = request.method()

        val commonParamsMap = HashMap<String, Any>()
        commonParamsMap[IMEI] = DeviceUtils.getIMEI(context)
        commonParamsMap[MODEL] = DeviceUtils.getModel()
        commonParamsMap[LANGUAGE] = DeviceUtils.getLanguage()
        commonParamsMap[OS] = DeviceUtils.getBuildVersionIncremental()
        commonParamsMap[RESOLUTION] = "${DensityUtil.getScreenW(context)}*${DensityUtil.getScreenH(context)}"
        commonParamsMap[SDK] = "${DeviceUtils.getBuildVersionSDK()}"
        commonParamsMap[DENSITY_SCALE_FACTOR] = "${context.resources.displayMetrics.density}"

        val token = ACache.get(context).getAsString(TOKEN)
        commonParamsMap[TOKEN] = token ?: ""

        when (method) {
            "GET" -> {
                val httpUrl = request.url()
                val rootMap = HashMap<String, Any?>()
                val paramNames = httpUrl.queryParameterNames()
                for (nameKey in paramNames) {
                    if (PARAM.equals(nameKey)) {
                        val oldParamJson = httpUrl.queryParameter(PARAM)
                        if (oldParamJson != null) {
                            // 原始参数
                            val p = gson.fromJson(oldParamJson, HashMap::class.java)
                            if (p != null) {
                                for ((key, value) in p) {
                                    rootMap[key as String] = value
                                }
                            }
                        }
                    } else {
                        rootMap[nameKey] = httpUrl.queryParameter(nameKey)
                    }
                }
                // 重新组装
                rootMap["publicParams"] = commonParamsMap
                // {"page":0,"publicParams":{"imei":'xxxxx',"sdk":14,.....}}
                val newJsonParams = gson.toJson(rootMap)

                var url = httpUrl.toString()
                val index = url.indexOf("?")
                if (index > 0) {
                    url = url.substring(0, index)
                }
                //http://112.124.22.238:8081/course_api/cniaoplay/featured?p= {"page":0,"publicParams":{"imei":'xxxxx',"sdk":14,.....}}
                url = "$url?$PARAM=$newJsonParams"
                Log.d(TAG, "intercept: $url")
                request = request.newBuilder().url(url).build()
            }
            "POST" -> {

                val body = request.body()
                var rootMap = HashMap<String, Any>()
                if (body is FormBody) {
                    for (i in 0 until body.size()) {
                        rootMap[body.encodedName(i)] = body.encodedValue(i)
                    }
                } else {
                    val buffer = Buffer()
                    body!!.writeTo(buffer)
                    val oldJsonParams = buffer.readUtf8()

                    if (!TextUtils.isEmpty(oldJsonParams)) {
                        //原始参数
                        val map: java.util.HashMap<*, *>? = gson.fromJson(oldJsonParams, HashMap::class.java)
                        rootMap = map as HashMap<String, Any>
                        //防止json格式错误转换出错
                        if (rootMap != null) {
                            rootMap["publicParams"] = commonParamsMap
                            val newJsonParams = gson.toJson(rootMap)
                            request = request.newBuilder().post(RequestBody.create(JSON, newJsonParams)).build()
                        }
                    }
                }
            }
        }
        return chain.proceed(request)
    }
}