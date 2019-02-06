package com.lckiss.pagecenter.injection.module

import com.lckiss.pagecenter.presenter.contarct.AppInfoContract
import dagger.Module
import dagger.Provides

/**
 * AppInfoModule定义 提供view
 */
@Module(includes = arrayOf(AppModelModule::class))
class AppInfoModule(private val mView: AppInfoContract.AppInfoView) {

    @Provides
    fun provideView(): AppInfoContract.AppInfoView {
        return mView
    }

}