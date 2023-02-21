package com.tes.android.projects.tvshowsapp.presentation.show_detail

import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail

data class ShowDetailState(
    val show: ShowDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val id:Int=0,
)