package com.lckiss.pagecenter.injection.module

import com.lckiss.baselib.injection.PerComponentScope
import com.lckiss.pagecenter.data.api.SubjectApi
import com.lckiss.pagecenter.data.model.SubjectModel
import com.lckiss.pagecenter.presenter.contarct.SubjectContract
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * SubjectModule
 */
@Module
class SubjectModule(private val mView: SubjectContract.ISubjectView) {

    @PerComponentScope
    @Provides
    fun provideModel(subjectApi: SubjectApi): SubjectContract.ISubjectModel {
        return SubjectModel(subjectApi)
    }

    @PerComponentScope
    @Provides
    fun provideView(): SubjectContract.ISubjectView {
        return mView
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): SubjectApi {
        return retrofit.create<SubjectApi>(SubjectApi::class.java)
    }
}