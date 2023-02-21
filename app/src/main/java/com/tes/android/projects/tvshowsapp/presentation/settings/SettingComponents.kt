package com.tes.android.projects.tvshowsapp.presentation.settings

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.tes.android.projects.tvshowsapp.R

class SettingComponents {
    fun getAllData(): List<SettingListModel> {
        return listOf(
            SettingListModel(
                imageVector = Icons.Default.Analytics,
                contentDescription = R.string.about,
                text = R.string.about_text
            ),
            SettingListModel(
                imageVector = Icons.Default.Anchor,
                contentDescription = R.string.version_,
                text = R.string.version_0_1
            )
        )
    }
}

data class SettingListModel(
    val imageVector: ImageVector,
    @StringRes val contentDescription: Int,
    @StringRes val text: Int
)