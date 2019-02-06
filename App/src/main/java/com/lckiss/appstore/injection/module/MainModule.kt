package com.lckiss.appstore.injection.module

import com.lckiss.appstore.data.api.MainApi
import com.lckiss.appstore.presenter.contract.MainContract
import com.lckiss.baselib.injection.ActivityScope
import com.lckiss.appstore.data.modle.MainModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * MainModule定义
 */
@Module
class MainModule(private val view: MainContract.MainView) {

    @ActivityScope
    @Provides
    fun provideView(): MainContract.MainView {
        return view
    }

    @ActivityScope
    @Provides
    fun provideModel(updateApi: MainApi): MainContract.IMainModel {
        return MainModel(updateApi)
    }

    @Provides
    fun provideMainApiService(retrofit: Retrofit): MainApi {
        return retrofit.create<MainApi>(MainApi::class.java)
    }


}