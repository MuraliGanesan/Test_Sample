package com.assesment.te.application.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assesment.te.listing.data.datasource.local.FavouriteProductDao
import com.assesment.te.listing.data.model.ProductData

@Database(entities = [ProductData::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun favouriteProductDao(): FavouriteProductDao
}