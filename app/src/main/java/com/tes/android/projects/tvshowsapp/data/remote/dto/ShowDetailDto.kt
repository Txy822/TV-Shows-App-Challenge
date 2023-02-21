package com.tes.android.projects.tvshowsapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ShowDetailDto(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: Image = Image(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("premiered")
    val premiered: String = "",
    @SerializedName("rating")
    val rating: Rating = Rating(),
    @SerializedName("runtime")
    val runtime: Int? = 0,
    @SerializedName("status")
    val status: String = "",
    @SerializedName("summary")
    val summary: String = "",
    @SerializedName("type")
    val type: String = ""
    )