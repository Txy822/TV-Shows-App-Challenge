package com.tes.android.projects.tvshowsapp.data.repository

import com.tes.android.projects.tvshowsapp.data.local.entity.FavoriteShowListingEntity
import com.tes.android.projects.tvshowsapp.data.local.entity.ShowListingEntity
import com.tes.android.projects.tvshowsapp.core.mapper.toFavoriteShowListingEntity
import com.tes.android.projects.tvshowsapp.core.mapper.toShowListing
import com.tes.android.projects.tvshowsapp.core.mapper.toShowListingEntity
import com.tes.android.projects.tvshowsapp.data.remote.dto.*
import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail
import com.tes.android.projects.tvshowsapp.domain.repository.ShowRepository
import com.tes.android.projects.tvshowsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository : ShowRepository {

    var showsList: MutableList<ShowDetail> = mutableListOf()
    var favoriteShowList: MutableList<ShowDetail> = mutableListOf()

    var shouldEmitError: Boolean = false

    override suspend fun getShowListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<ShowDetail>>> {
        return flow {
            emit(Resource.Loading())
            if (!shouldEmitError) emit(Resource.Success(getDummyResponse().map { it.toShowListing() }))
            else  emit(Resource.Error("Error message"))
        }
    }

    override suspend fun getShowListingFromDb(query: String): List<ShowListingEntity> {
        return showsList.map { it.toShowListingEntity() }
    }

    override suspend fun getShowListingFromApi(): ShowsDto {
        return null!!
    }

    override suspend fun getShowInfo(query: String): Resource<ShowDetail> {
        return Resource.Success( data= showsList[0])
    }

    override suspend fun clearShowListingsFromDb() {
        showsList.clear()
    }

    override suspend fun insertShowListingToDb(showList: List<ShowListingEntity>) {
       for( list in showList){
           showsList.add(list.toShowListing())
       }
    }


    override suspend fun insertFavoriteShowToDb(show: ShowDetail) {
        favoriteShowList.add(show)
    }

    override suspend fun getFavorites(): Flow<Resource<List<ShowDetail>>> =flow{
        emit(Resource.Success(data=favoriteShowList))
    }

    override suspend fun getSingleShowFromDB(query: String): ShowListingEntity {
        return showsList[0].toShowListingEntity()
    }

    override suspend fun deleteFavoriteById(favoriteId: Int) {
        favoriteShowList.removeAt(favoriteId)
    }

    override suspend fun getAllFavoritesFromDb(): List<FavoriteShowListingEntity> {
        return favoriteShowList.map { it.toFavoriteShowListingEntity() }
    }
}

fun getDummyResponse() = getDummyList()

fun getDummyList(): List<ShowDetailDto> {

    val list = mutableListOf<ShowDetailDto>()
    for (i in 1..5) {
        list.add(getDummyShowInfoDto(i))
    }
    return  list
}

fun getDummyShowInfoDto(id: Int = 1) = ShowDetailDto(
    id = id,
    image = Image(),
    name = "Under the Dome",
    premiered = "2013-06-24",
    rating = Rating(),
    runtime = 60,
    status = "Ended",
    summary = "Under the Dome is the story of a small town that is suddenly and inexplicably sealed off from the rest of the world by an enormous transparent dome. The town's inhabitants must deal with surviving the post-apocalyptic conditions while searching for answers about the dome, where it came from and if and when it will go away",
    type = "Scripted"
)


