package com.tes.android.projects.tvshowsapp.presentation.home

import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail

sealed class ShowsEvent {
        object LoadShows : ShowsEvent()
        data class OnFavoriteSelected(val show: ShowDetail):ShowsEvent()
        data class DeleteFavorite(val id: Int) : ShowsEvent()
}