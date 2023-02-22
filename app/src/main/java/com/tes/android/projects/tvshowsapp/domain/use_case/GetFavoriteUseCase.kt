package com.tes.android.projects.tvshowsapp.domain.use_case

import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail
import com.tes.android.projects.tvshowsapp.domain.repository.ShowRepository
import com.tes.android.projects.tvshowsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteUseCase  @Inject constructor(
    private val repository: ShowRepository
){
    suspend fun getFavorites(): Flow<Resource<List<ShowDetail>>> {
        return  repository.getFavorites()
    }
}