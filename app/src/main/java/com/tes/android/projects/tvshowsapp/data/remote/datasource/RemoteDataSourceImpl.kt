package com.tes.android.projects.tvshowsapp.data.remote.datasource

import com.tes.android.projects.tvshowsapp.data.remote.apiservice.TvShowApi
import com.tes.android.projects.tvshowsapp.data.remote.dto.ShowsDto
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: TvShowApi
    ):RemoteDataSource
{
    override suspend fun getShowListingFromApi(): ShowsDto =api.getListings()
}