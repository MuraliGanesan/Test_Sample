package com.assesment.te.listing.data.repository

import com.assesment.te.application.data.RemoteResult
import com.assesment.te.listing.data.datasource.network.ProductListDataSource
import com.assesment.te.listing.data.model.ProductData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductListingRepository @Inject constructor(
    private val productListDataSource: ProductListDataSource
) {

    suspend fun addProduct(productData: ProductData) = withContext(Dispatchers.IO) {
        return@withContext productListDataSource.addProductItem(productData)
    }

    suspend fun removeProduct(productData: ProductData) = withContext(Dispatchers.IO) {
        return@withContext productListDataSource.removeProductItem(productData)
    }

    suspend fun getProductList(count: Int): RemoteResult<List<ProductData>> =
        withContext(Dispatchers.IO) {
            return@withContext productListDataSource.getProductList(count)
        }

    suspend fun getFavouriteList(): RemoteResult<List<ProductData>> = withContext(Dispatchers.IO) {
        return@withContext productListDataSource.getFavouriteList()
    }
}