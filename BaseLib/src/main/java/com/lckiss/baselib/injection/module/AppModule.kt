package com.lckiss.baselib.injection.module

import android.app.Application
import com.google.gson.Gson
import com.lckiss.baselib.common.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * AppModule定义 提供全局的application与Gson
 */
@Module
class AppModule(private val application: BaseApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}