package com.tes.android.projects.tvshowsapp.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tes.android.projects.tvshowsapp.R


@Composable
fun SettingScreen(
    ) {
    Box(modifier = Modifier.fillMaxSize()) {
        Surface(color = MaterialTheme.colors.background) {
           Column() {
               TopAppBarContent()
               SettingList()
           }

        }
    }
}

@Composable
fun SettingList() {
    val settingListRepo = SettingComponents()
    val getAllData = settingListRepo.getAllData()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        item {
            Box(modifier = Modifier.fillMaxWidth().padding(start=60.dp, top=10.dp),

                ) {
                Text(text = "About the App")
            }
        }
        itemsIndexed(items = getAllData) { index, settingListModel ->
            SettingListItem(settingListModel = settingListModel)
        }
    }
}


@Composable
fun SettingListItem(settingListModel: SettingListModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = settingListModel.imageVector,
            contentDescription = stringResource(id = settingListModel.contentDescription),
            tint = Color.White,
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(
            modifier = Modifier.wrapContentSize(),
            text = stringResource(id = settingListModel.text)
        )
    }
}

@Composable
fun TopAppBarContent() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.setting_top_app_bar),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp,
    )
}