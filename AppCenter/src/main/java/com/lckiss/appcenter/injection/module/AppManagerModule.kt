package com.lckiss.appcenter.injection.module

import android.app.Application
import com.lckiss.appcenter.data.model.AppManagerModel
import com.lckiss.appcenter.presenter.contract.AppManagerContract
import dagger.Module
import dagger.Provides

/**
 * AppManagerModule
 */
@Module
class AppManagerModule(private val mView: AppManagerContract.IAppManagerView) {

    @Provides
    fun provideView(): AppManagerContract.IAppManagerView {
        return mView
    }

    @Provides
    fun provideModel(application: Application): AppManagerContract.IAppManagerModel {
        return AppManagerModel(application)
    }

}