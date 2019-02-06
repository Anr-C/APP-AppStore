package com.lckiss.pagecenter.injection.module

import com.lckiss.pagecenter.data.api.AppInfoApi
import com.lckiss.pagecenter.data.model.AppInfoModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * 提供多个页面通用的AppInfoModel与AppInfoApi
 */
@Module
class AppModelModule {

    @Provides
    fun provideModel(appInfoApi: AppInfoApi): AppInfoModel {
        return AppInfoModel(appInfoApi)
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): AppInfoApi {
        return retrofit.create<AppInfoApi>(AppInfoApi::class.java)
    }

}