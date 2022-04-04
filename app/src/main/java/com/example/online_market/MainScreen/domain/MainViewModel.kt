package com.example.online_market.MainScreen.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.online_market.MainScreen.repository.data.CategoryDataSource
import com.example.online_market.MainScreen.repository.data.CategoryDto
import com.example.online_market.MainScreen.repository.repositories.interfaces.MainInfoRepository
import com.example.online_market.MainScreen.repository.retrofit.entities.BestSeller
import com.example.online_market.MainScreen.repository.retrofit.entities.HomeStore
import com.example.online_market.helpers.ViewStateScreen
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val mainInfoRepositoryImpl: MainInfoRepository,
    private val categoryDataSourceImpl: CategoryDataSource

) : ViewModel() {

    // init CoroutineExceptionHandler
    private val errorHandler = CoroutineExceptionHandler { _, error ->
        _viewState.postValue(ViewStateScreen(e = error))
    }

    // init LiveData
    // category
    val categoryList: LiveData<List<CategoryDto>> get() = _categoryList
    private val _categoryList = MutableLiveData<List<CategoryDto>>()

    // mainInfo
    val homeStore: LiveData<List<HomeStore>> get() = _homeStore
    private val _homeStore = MutableLiveData<List<HomeStore>>()
    val bestSeller: LiveData<List<BestSeller>> get() = _bestSeller
    private val _bestSeller = MutableLiveData<List<BestSeller>>()
    val viewState: LiveData<ViewStateScreen> get() = _viewState
    private val _viewState = MutableLiveData<ViewStateScreen>()

    fun getCategory() {
        _categoryList.postValue(categoryDataSourceImpl.getCategory())
    }

    fun getMainInfo(apiKey: String) {
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO) {
                val mainInfoResponse = mainInfoRepositoryImpl.getMainInfo(apiKey)
                Log.d("mainInfoResponse", mainInfoResponse.body()!![0].homeStore.toString())
                if (mainInfoResponse.isSuccessful) {
                    val homeStoreList = mainInfoResponse.body()?.get(0)?.homeStore ?: emptyList()
                    val bestSellerList = mainInfoResponse.body()?.get(0)?.bestSeller ?: emptyList()
                    _homeStore.postValue(homeStoreList)
                    _bestSeller.postValue(bestSellerList)
                    _viewState.postValue(ViewStateScreen(true))
                }
            }
        }
    }
}