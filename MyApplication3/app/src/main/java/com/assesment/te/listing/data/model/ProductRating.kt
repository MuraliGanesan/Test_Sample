package com.assesment.te.listing.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductRating(
    val rate: Double,
    val count: Int
):Parcelable