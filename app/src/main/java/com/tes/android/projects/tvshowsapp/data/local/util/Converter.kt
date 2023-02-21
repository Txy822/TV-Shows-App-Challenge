package com.tes.android.projects.tvshowsapp.data.local.util

import androidx.compose.material.ContentAlpha.medium
import androidx.room.TypeConverter
import com.tes.android.projects.tvshowsapp.data.remote.dto.Image
import com.tes.android.projects.tvshowsapp.data.remote.dto.Rating

class Converter {
    @TypeConverter
    fun fromImageToString(image: Image):String {
        return image.medium
    }
    @TypeConverter
    fun fromStringToImage(medium:String): Image {
        return Image(medium=medium)
    }

    @TypeConverter
    fun fromRatingToDouble(rating: Rating): Double? {
        return rating.average
    }
    @TypeConverter
    fun fromDoubleToRating(average:Double): Rating {
        return Rating(average=average)
    }
}
