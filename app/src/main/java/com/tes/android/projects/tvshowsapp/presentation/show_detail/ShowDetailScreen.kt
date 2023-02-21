package com.tes.android.projects.tvshowsapp.presentation.show_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.tes.android.projects.tvshowsapp.core.components.RatingBar
import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail
import com.tes.android.projects.tvshowsapp.presentation.search_shows.FavoriteButton
import com.tes.android.projects.tvshowsapp.ui.theme.DarkBlue

@Composable
fun ShowDetailScreen(
    viewModel: ShowDetailViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state
    val show = state.show

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .padding(16.dp)
    ) {
        show?.let { show ->
            val imagePainter = rememberAsyncImagePainter(show.image.medium)
            val showName = show.name
            TopAppBarContent(
                navController,
                showName
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.padding(20.dp)) {
                Image(
                    painter = imagePainter,
                    contentDescription = "",
                    Modifier.size(300.dp)
                )
                FavoriteButton(show = show)

            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Name: ${show.name}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Type: ${show.type}",
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Premiered:  ${show.premiered.toString()}",
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Status: ${show.status}",
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Run time : ${show.runtime}",
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.padding()) {
                RatingBar(rating = show.rating.average)
                Text(
                    text = "${show.rating.average}",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Summary of ${show.name} : ${show.summary.toString()}",
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
    if (state.error !=null) {

        Text(
            text = state.error,
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            //  .align(Alignment.Center)
        )
        if (state.isLoading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomCircularProgressBar()
            }
        }
    }
}

@Composable
private fun CustomCircularProgressBar(){
    CircularProgressIndicator(
        modifier = Modifier.size(100.dp),
        color = Color.Green,
        strokeWidth = 10.dp)

}
@Composable
fun TopAppBarContent(navController: NavController, name: String) {
    TopAppBar(
        title = {
            Text(
                text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp,
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Go back",
                )
            }
        }
    )
    @Composable
    fun FavoriteButton(
        viewModel: ShowDetailViewModel = hiltViewModel(),
        show: ShowDetail
    ) {
        var isFavorite by rememberSaveable(show) { mutableStateOf(show.isFavorite) }

        IconButton(onClick = {
            isFavorite = !isFavorite
            show.isFavorite = isFavorite
            if (isFavorite) {
                viewModel.onEvent(ShowDetailEvent.OnFavoriteSelected(show))
            } else {
                viewModel.onEvent(ShowDetailEvent.OnDeleteSelected(show.id))
            }
        }) {
            val tintColor = if (isFavorite) Color.Red else Color.White
            Icon(
                painter = rememberVectorPainter(Icons.Default.Favorite),
                contentDescription = null,
                tint = tintColor
            )
        }
    }

}
