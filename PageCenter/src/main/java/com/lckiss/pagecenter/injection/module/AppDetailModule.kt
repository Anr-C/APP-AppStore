package com.lckiss.pagecenter.injection.module

import com.lckiss.pagecenter.presenter.contarct.AppInfoContract
import dagger.Module
import dagger.Provides

/**
 * AppDetailModule
 */
@Module(includes = arrayOf(AppModelModule::class))
class AppDetailModule(private val mView: AppInfoContract.AppDetailView) {

    @Provides
    fun provideView(): AppInfoContract.AppDetailView {
        return mView
    }

}