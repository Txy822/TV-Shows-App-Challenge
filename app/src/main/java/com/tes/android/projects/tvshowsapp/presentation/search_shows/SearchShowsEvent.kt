package com.tes.android.projects.tvshowsapp.presentation.search_shows

import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail


sealed class SearchShowsEvent {
    object Refresh : SearchShowsEvent()
    object LoadShows : SearchShowsEvent()
    data class OnSearchQueryChange(val query: String) : SearchShowsEvent()
    data class OnFavoriteSelected(val show:ShowDetail):SearchShowsEvent()
    data class DeleteFavorite(val id: Int) : SearchShowsEvent()

}
