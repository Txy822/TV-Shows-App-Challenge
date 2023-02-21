package com.tes.android.projects.tvshowsapp.presentation.favorite_shows

sealed class FavoriteShowsEvent{
    data class OnDeleteSelected(val id: Int) : FavoriteShowsEvent()
    object LoadFavoriteShows : FavoriteShowsEvent()
}
