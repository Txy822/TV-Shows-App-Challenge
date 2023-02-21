package com.tes.android.projects.tvshowsapp.data.remote.datasource

import com.tes.android.projects.tvshowsapp.data.remote.dto.ShowsDto

interface RemoteDataSource {

    suspend fun getShowListingFromApi(): ShowsDto
}