package com.assesment.te.listing.data.datasource.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assesment.te.listing.data.model.ProductData

@Dao
interface FavouriteProductDao {
    @Query("SELECT * FROM ProductData")
    fun getFavouriteList(): List<ProductData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavourite(vararg productData: ProductData)

    @Query("DELETE FROM ProductData WHERE id = :id")
    fun deleteFavourite(id: Int)

}