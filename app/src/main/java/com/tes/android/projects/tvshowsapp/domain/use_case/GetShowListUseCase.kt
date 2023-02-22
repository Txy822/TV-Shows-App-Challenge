package com.tes.android.projects.tvshowsapp.domain.use_case

import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail
import com.tes.android.projects.tvshowsapp.domain.repository.ShowRepository
import com.tes.android.projects.tvshowsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShowListUseCase @Inject constructor(
    private val repository:ShowRepository
){
    suspend fun getShowList(query: String, fetchFromRemote: Boolean): Flow<Resource<List<ShowDetail>>> {
        return  repository.getShowListings( fetchFromRemote = fetchFromRemote,query=query)
    }
}