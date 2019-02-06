package com.lckiss.searchcenter.injection.module

import com.lckiss.baselib.injection.PerComponentScope
import com.lckiss.searchcenter.data.api.SearchApi
import com.lckiss.searchcenter.data.model.SearchModel
import com.lckiss.searchcenter.presenter.contract.SearchContract
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * SearchModule
 */
@Module
class SearchModule(private val view: SearchContract.ISearchView) {

    @PerComponentScope
    @Provides
    fun provideModel(searchApi: SearchApi): SearchContract.ISearchModel {
        return SearchModel(searchApi)
    }

    @PerComponentScope
    @Provides
    fun provideView(): SearchContract.ISearchView {
        return view
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): SearchApi {
        return retrofit.create<SearchApi>(SearchApi::class.java)
    }
}
