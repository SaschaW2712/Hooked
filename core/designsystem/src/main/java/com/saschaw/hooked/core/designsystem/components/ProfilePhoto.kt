package com.saschaw.hooked.core.designsystem.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.saschaw.hooked.core.designsystem.R

@Composable
fun ProfilePhoto(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    url: String?
) {
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(url)
            .crossfade(true)
            .build(),
        fallback = painterResource(R.drawable.app_logo),
        modifier = modifier.clip(CircleShape),
        contentScale = ContentScale.FillBounds,
        contentDescription = contentDescription
    )
}