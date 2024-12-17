package com.saschaw.hooked.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.saschaw.hooked.core.designsystem.R
import com.saschaw.hooked.core.model.PatternListItem

@Composable
fun PatternCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    pattern: PatternListItem,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
    ) {
        val context = LocalContext.current

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(pattern.firstPhoto?.mediumUrl)
                    .crossfade(true)
                    .build(),
                fallback = painterResource(R.drawable.app_logo),
                modifier = Modifier
                    .fillMaxSize()
                    .height(250.dp),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
            )

            Column(
                Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(pattern.patternAuthor?.users?.firstOrNull()?.photoUrl)
                            .crossfade(true)
                            .build(),
                        fallback = painterResource(R.drawable.app_logo),
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "Author profile image"
                    )

                    Column {
                        Text(pattern.name, style = MaterialTheme.typography.titleMedium)

                        pattern.patternAuthor?.name?.let { authorName ->
                            Text(authorName, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}