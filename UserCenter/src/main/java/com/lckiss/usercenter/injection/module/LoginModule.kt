package com.lckiss.usercenter.injection.module

import com.lckiss.usercenter.data.api.LoginApi
import com.lckiss.usercenter.data.model.LoginModel
import com.lckiss.usercenter.presenter.contract.LoginContract
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * LoginModule定义
 */
@Module
class LoginModule( var mView :LoginContract.LoginView) {

    @Provides
    fun provideView(): LoginContract.LoginView {
        return mView
    }

    @Provides
    fun provideModel(loginApi: LoginApi): LoginContract.ILoginModel {
        return LoginModel(loginApi)
    }

    @Provides
    fun provideLoginApiService(retrofit: Retrofit): LoginApi {
        return retrofit.create<LoginApi>(LoginApi::class.java)
    }
}