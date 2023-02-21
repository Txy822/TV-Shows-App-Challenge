package com.tes.android.projects.tvshowsapp.domain.repository

import com.tes.android.projects.tvshowsapp.data.local.entity.FavoriteShowListingEntity
import com.tes.android.projects.tvshowsapp.data.local.entity.ShowListingEntity
import com.tes.android.projects.tvshowsapp.data.remote.dto.ShowsDto
import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail
import com.tes.android.projects.tvshowsapp.util.Resource
import kotlinx.coroutines.flow.Flow


interface ShowRepository {

    suspend fun getShowListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<ShowDetail>>>

    suspend fun getShowListingFromDb(
        query: String
    ): List<ShowListingEntity>

    suspend fun getShowListingFromApi(): ShowsDto

    suspend fun clearShowListingsFromDb()

    suspend fun insertShowListingToDb(
        showList: List<ShowListingEntity>
    )
    suspend fun getShowInfo(query: String):Resource<ShowDetail>

    suspend fun insertFavoriteShowToDb(show: ShowDetail)

    suspend fun getFavorites():Flow<Resource<List<ShowDetail>>>

    suspend fun deleteFavoriteById(favoriteId: Int)

    suspend fun getAllFavoritesFromDb(): List<FavoriteShowListingEntity>

    suspend fun getSingleShowFromDB(query: String): ShowListingEntity
}

