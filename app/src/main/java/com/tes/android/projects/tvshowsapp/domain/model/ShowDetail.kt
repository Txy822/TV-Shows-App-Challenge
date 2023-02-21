package com.tes.android.projects.tvshowsapp.domain.model

import com.tes.android.projects.tvshowsapp.data.remote.dto.Image
import com.tes.android.projects.tvshowsapp.data.remote.dto.Rating

data class ShowDetail(
    val id: Int = 0,
    val name: String,
    val type: String,
    val runtime: Int,
    val image: Image,
    val premiered: String,
    val status: String,
    val summary: String,
    val rating: Rating,
    var isFavorite: Boolean = false
)
