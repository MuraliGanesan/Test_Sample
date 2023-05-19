package com.assesment.te.listing.data.datasource.network

import android.util.Log
import com.assesment.te.application.data.RemoteResult
import com.assesment.te.application.database.LocalDatabase
import com.assesment.te.application.network.RetrofitService
import com.assesment.te.listing.data.model.ProductData
import javax.inject.Inject

class ProductListDataSource @Inject constructor(
    private val service: RetrofitService,
    private val db: LocalDatabase
) {
    fun addProductItem(productData: ProductData) {
        db.favouriteProductDao().addFavourite(productData)
    }

    fun removeProductItem(productData: ProductData) {
        db.favouriteProductDao().deleteFavourite(productData.id)
    }

    fun getFavouriteList(): RemoteResult<List<ProductData>> {
        return try {
            val list = db.favouriteProductDao().getFavouriteList()
            if (list.isEmpty()) {
                RemoteResult.NullOrEmpty
            } else {
                RemoteResult.Success(list)
            }
        } catch (e: Exception) {
            RemoteResult.Failure(e)
        }
    }

    suspend fun getProductList(count: Int): RemoteResult<List<ProductData>> {

//        val productList = listOf(
//            ProductData(
//                id = 1,
//                category = "category1",
//                description = "description 1",
//                image = "URL",
//                price = 10.50,
//                title = "title",
//                rating = ProductRating(rate = 4.0, count = 30),
//                isFavourite = false
//            ),
//            ProductData(
//                id = 2,
//                category = "category2",
//                description = "description 2",
//                image = "URL",
//                price = 10.50,
//                title = "title",
//                rating = ProductRating(rate = 4.0, count = 30),
//                isFavourite = false
//            )
//        )
//        return RemoteResult.Success(productList)


        return try {
            val result = service.getProductListFromNetWork(limit = count)
            if (result.isSuccessful) {
                RemoteResult.Success(result.body())
            } else {
                Log.e("TAG", "response else----->")
                RemoteResult.NullOrEmpty
            }
        } catch (e: Exception) {
            e.printStackTrace()
            RemoteResult.Failure(e)
        }

    }
}