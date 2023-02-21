package com.tes.android.projects.tvshowsapp.domain.use_case

import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail
import com.tes.android.projects.tvshowsapp.domain.repository.ShowRepository
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val repository: ShowRepository
) {
    suspend fun deleteFavorite(id: Int) {
        repository.deleteFavoriteById(id)
    }
}