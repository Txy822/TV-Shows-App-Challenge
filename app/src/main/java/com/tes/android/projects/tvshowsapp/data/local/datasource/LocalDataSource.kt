package com.tes.android.projects.tvshowsapp.data.local.datasource

import com.tes.android.projects.tvshowsapp.data.local.entity.FavoriteShowListingEntity
import com.tes.android.projects.tvshowsapp.data.local.entity.ShowListingEntity
import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail

interface LocalDataSource {

    suspend fun deleteFavoriteById(favoriteId: Int)

    suspend fun getAllFavoritesFromDb(): List<FavoriteShowListingEntity>

    suspend fun insertShowListingToDb(showList: List<ShowListingEntity>)

    suspend fun insertFavoriteShowToDb(show: ShowDetail)

    suspend fun clearShowListingsFromDb()

    suspend fun getSingleShowFromDB(query:String):ShowListingEntity

    suspend fun getShowListingFromDb(query: String): List<ShowListingEntity>


}