package com.tes.android.projects.tvshowsapp.presentation.favorite_shows

import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail

data class FavoriteShowsUiState(
    val favoriteShows: List<ShowDetail> = emptyList(),
    val id:Int=0,
    val isLoading:Boolean =false,
    val error:String =""
    )
