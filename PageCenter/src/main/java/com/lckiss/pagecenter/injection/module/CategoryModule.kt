package com.lckiss.pagecenter.injection.module

import com.lckiss.pagecenter.data.api.CategoryApi
import com.lckiss.pagecenter.data.model.CategoryModel
import com.lckiss.pagecenter.presenter.contarct.CategoryContract
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * CategoryModule
 */
@Module
class CategoryModule(private val mView: CategoryContract.CategoryView) {

    @Provides
    fun provideView(): CategoryContract.CategoryView {
        return mView
    }

    @Provides
    fun provideModel(categoryApi: CategoryApi): CategoryContract.ICategoryModel {
        return CategoryModel(categoryApi)
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): CategoryApi {
        return retrofit.create<CategoryApi>(CategoryApi::class.java)
    }
}