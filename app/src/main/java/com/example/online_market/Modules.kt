package com.example.online_market

import android.content.Context
import CartScreen.domain.retrofit.CartApiService
import com.example.online_market.CartScreen.domain.CartViewModel
import com.example.online_market.CartScreen.repository.repositories.CartRepositoryImpl
import com.example.online_market.CartScreen.repository.repositories.interfaces.CartRepository
import com.example.online_market.DetailScreen.repository.retrofit.DetailApiService
import com.example.online_market.DetailScreen.domain.DetailViewModel
import com.example.online_market.DetailScreen.repository.repositories.interfaces.DetailInfoRepository
import com.example.online_market.DetailScreen.repository.repositories.DetailInfoRepositoryImpl
import com.example.online_market.MainScreen.repository.retrofit.MainApiService
import com.example.online_market.MainScreen.domain.MainViewModel
import com.example.online_market.MainScreen.repository.data.CategoryDataSource
import com.example.online_market.MainScreen.repository.data.CategoryDataSourceImpl
import com.example.online_market.MainScreen.repository.repositories.MainInfoRepositoryImpl
import com.example.online_market.MainScreen.repository.repositories.interfaces.MainInfoRepository
import com.example.online_market.utils.RetrofitExtensions.Companion.setClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { CartViewModel(get()) }
}

val apiServiceModule = module {
    fun provideMainApiService(retrofit: Retrofit): MainApiService {
        return retrofit.create(MainApiService::class.java)
    }
    fun provideDetailApiService(retrofit: Retrofit): DetailApiService {
        return retrofit.create(DetailApiService::class.java)
    }
    fun provideCartApiService(retrofit: Retrofit): CartApiService {
        return retrofit.create(CartApiService::class.java)
    }
    single { provideMainApiService(get()) }
    single { provideDetailApiService(get()) }
    single { provideCartApiService(get()) }
}

val retrofitModule = module {
    fun provideRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .setClient()
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { provideRetrofit(get()) }
}

val repositoryModule = module {
    fun provideMainInfoRepository(mainApiService: MainApiService): MainInfoRepository {
        return MainInfoRepositoryImpl(mainApiService)
    }
    fun provideDetailRepository(detailApiService: DetailApiService): DetailInfoRepository {
        return DetailInfoRepositoryImpl(detailApiService)
    }
    fun provideCartRepository(cartApiService: CartApiService): CartRepository {
        return CartRepositoryImpl(cartApiService)
    }
    fun provideCategoryDataSource(): CategoryDataSource {
        return CategoryDataSourceImpl()
    }
    single { provideMainInfoRepository(get()) }
    single { provideDetailRepository(get()) }
    single { provideCartRepository(get()) }
    single { provideCategoryDataSource() }
}
