package com.tes.android.projects.tvshowsapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tes.android.projects.tvshowsapp.data.local.dao.FavoriteShowDao
import com.tes.android.projects.tvshowsapp.data.local.dao.TvShowDao
import com.tes.android.projects.tvshowsapp.data.local.entity.FavoriteShowListingEntity
import com.tes.android.projects.tvshowsapp.data.local.entity.ShowListingEntity
import com.tes.android.projects.tvshowsapp.data.local.util.Converter

@Database(
    entities = [ShowListingEntity::class, FavoriteShowListingEntity::class],
    version = 12,
    exportSchema = false
)

@TypeConverters(Converter::class)
abstract class TvShowDatabase:RoomDatabase() {
    abstract fun showDao(): TvShowDao
    abstract fun favoriteShowDao(): FavoriteShowDao
}