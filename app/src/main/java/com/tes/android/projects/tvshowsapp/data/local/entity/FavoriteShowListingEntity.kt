package com.tes.android.projects.tvshowsapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tes.android.projects.tvshowsapp.data.remote.dto.Image
import com.tes.android.projects.tvshowsapp.data.remote.dto.Rating

@Entity(tableName = "favorite_show_listing_table")
data class FavoriteShowListingEntity(
    val name:String,
    val type:String,
    val runtime: Int,
    val image : Image,
    val premiered: String,
    val status: String,
    val summary: String,
    val rating: Rating,
    @PrimaryKey val id: Int? = null
)
