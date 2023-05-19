package com.assesment.te.listing.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(indices = [Index(value = ["id"], unique = true)])
data class ProductData(
    @PrimaryKey(autoGenerate = true) val uId: Int = 0,
    @ColumnInfo val id: Int,
    @ColumnInfo val title: String,
    @ColumnInfo val price: Double,
    @ColumnInfo val description: String,
    @ColumnInfo val category: String,
    @ColumnInfo val image: String,
    @Embedded val rating: ProductRating,
    var isFavourite: Boolean
) : Parcelable
