package com.lckiss.pagecenter.injection.module

import com.lckiss.pagecenter.data.api.AppInfoApi
import com.lckiss.pagecenter.data.model.AppInfoModel
import com.lckiss.pagecenter.presenter.contarct.AppInfoContract
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * 推荐页面Module
 */
@Module(includes = arrayOf(AppModelModule::class))
class RecommendModule(private val mView: AppInfoContract.View) {

    @Provides
    fun provideView(): AppInfoContract.View {
        return mView
    }
}
