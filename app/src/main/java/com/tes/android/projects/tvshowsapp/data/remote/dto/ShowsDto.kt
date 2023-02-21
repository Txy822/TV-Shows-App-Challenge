package com.tes.android.projects.tvshowsapp.data.remote.dto

import com.google.gson.annotations.SerializedName

class ShowsDto : ArrayList<ShowDetailDto>()

data class Image(
    @SerializedName("medium")
    val medium: String = "",
    @SerializedName("original")
    val original: String = ""
)
data class Rating(
    @SerializedName("average")
    val average: Double = 0.0
)


