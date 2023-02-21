package com.tes.android.projects.tvshowsapp.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail

@Composable
fun ShowItem(
    show: ShowDetail,
    modifier: Modifier = Modifier
) {
    val imagePainter = rememberAsyncImagePainter(show.image.medium)
    Row(
        modifier = modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(start = 2.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                modifier = Modifier
                    .height(150.dp)
                    .width(100.dp),
                painter = imagePainter, contentDescription = "Show",
            )
        }
        Column {
            Text(
                text = show.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(start = 20.dp)
            )
            Text(
                text = show.type,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(start = 20.dp)
            )
            Text(
                text = "(${show.runtime})",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.padding(start = 20.dp)) {
                RatingBar(rating = show.rating.average)
                Text(
                    text = "${show.rating.average}",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}