package com.tes.android.projects.tvshowsapp.presentation.show_detail

import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail

sealed class ShowDetailEvent{
    data class OnDeleteSelected(val id: Int) : ShowDetailEvent()
    data class OnFavoriteSelected(val show: ShowDetail): ShowDetailEvent()
}
