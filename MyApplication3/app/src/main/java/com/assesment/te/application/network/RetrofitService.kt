package com.assesment.te.application.network

import com.assesment.te.listing.data.model.ProductData
import com.assesment.te.listing.data.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("products")
    suspend fun getProductListFromNetWork(@Query("limit") limit: Int): Response<List<ProductData>>
}