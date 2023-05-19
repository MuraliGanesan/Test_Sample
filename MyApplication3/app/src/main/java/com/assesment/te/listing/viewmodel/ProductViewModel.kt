package com.assesment.te.listing.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assesment.te.application.data.RemoteResult
import com.assesment.te.listing.data.model.ProductData
import com.assesment.te.listing.data.repository.ProductListingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val LIMIT = 10

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductListingRepository) :
    ViewModel() {

    private val _favouriteList: MutableLiveData<ProductListState> by lazy {
        MutableLiveData<ProductListState>(ProductListState.Loading)
    }
    val favouriteList: LiveData<ProductListState> = _favouriteList

    private val _productList: MutableLiveData<ProductListState> by lazy {
        MutableLiveData<ProductListState>(ProductListState.Loading)
    }
    val productList: LiveData<ProductListState> = _productList

    private var remoteList: List<ProductData>? = null
    private var localList: List<ProductData>? = null

    fun addFavourite(productData: ProductData) {
        viewModelScope.launch {
            repository.addProduct(productData)
            getRefreshData()
        }

    }

    fun removeFavourite(productData: ProductData) {
        viewModelScope.launch {
            Log.e("TAG", "remove----->")
            repository.removeProduct(productData)
            getRefreshData()
        }

    }

    fun getFavouriteList() {
        viewModelScope.launch {
            when (val result = repository.getFavouriteList()) {
                is RemoteResult.Success -> {
                    _favouriteList.value = result.data?.let { ProductListState.Success(it) }
                }

                is RemoteResult.Failure -> {
                    _favouriteList.value = ProductListState.Failure
                }

                else -> {
                    //Nothing
                }
            }
        }
    }

    fun getRemoteData() = viewModelScope.async {
        when (val result = repository.getProductList(LIMIT)) {
            is RemoteResult.Success -> {
                remoteList = result.data

            }

            else -> {
                //Nothing
            }
        }
    }

    fun getRefreshData() = viewModelScope.launch {
        when (val result = repository.getFavouriteList()) {
            is RemoteResult.Success -> {
                Log.e("TAG", "refresh----->" + result.data)
                localList = result.data
                processList()
            }

            else -> {
                //Nothing
            }
        }
    }

    fun getLocalData() = viewModelScope.async {
        when (val result = repository.getFavouriteList()) {
            is RemoteResult.Success -> {
                localList = result.data

            }

            else -> {
                //Nothing
            }
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            awaitAll(
                getRemoteData(),
                getLocalData()
            )
            processList()
        }
    }


    private fun processList() {
        remoteList?.forEach { remotedata ->
            localList?.forEach { locatdata ->
                if (locatdata.id == remotedata.id) {
                    remotedata.isFavourite = true
                }
            }
        }
        Log.e("TAG","remoteList----->"+remoteList)
        _productList.value = remoteList?.let { ProductListState.Success(it) }
    }

    sealed class ProductListState {
        object Loading : ProductListState()
        class Success(val productList: List<ProductData>) : ProductListState()
        object Failure : ProductListState()
    }

}