package com.lckiss.baselib.injection.component

import android.app.Application
import com.google.gson.Gson
import com.lckiss.baselib.injection.module.AppModule
import com.lckiss.baselib.injection.module.HttpModule
import com.lckiss.baselib.rx.RxErrorHandler
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * AppComponent
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, HttpModule::class))
interface AppComponent {

    fun getApplication(): Application
    fun getGson():Gson
    fun getRxErrorHandler(): RxErrorHandler
    fun getRetrofit(): Retrofit

}