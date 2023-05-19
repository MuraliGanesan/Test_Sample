package com.assesment.te.application.data

import com.assesment.te.listing.data.model.ProductData

sealed class RemoteResult<out T : Any> {
    class Success<out T : Any>(val data: List<ProductData>?) : RemoteResult<T>()
    class Failure(val error: Throwable, val code: Int? = null) : RemoteResult<Nothing>()
    object NullOrEmpty : RemoteResult<Nothing>()
}
