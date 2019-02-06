package com.lckiss.baselib.injection.module

import android.app.Application
import com.google.gson.Gson
import com.lckiss.baselib.common.BaseConstant.Companion.BASE_URL
import com.lckiss.baselib.data.net.CommonParamsInterceptor
import com.lckiss.baselib.rx.RxErrorHandler
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * 提供通用Http模块
 */
@Module
class HttpModule {

    /**
     * 提供OkHttpClient
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application, gson: Gson): OkHttpClient {
        //log日志拦截器
        val loggingInterceptor = HttpLoggingInterceptor();

        //开发模式记录整个body，否则只记录信息 如返回200，http版本协议号等
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS;

        //如果用到https 我们需要创建SSLSocketFactory，并设置到Client
        //        SSLSocketFactory sslSocketFactory=null;
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(CommonParamsInterceptor(gson, application))
                //连接超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
    }

    /**
     * 提供Retrofit
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient).build()
    }

    /**
     * 提供RxErrorHandler
     */
    @Provides
    @Singleton
    fun provideErrorHandle(application: Application): RxErrorHandler {
        return RxErrorHandler(application)
    }

}