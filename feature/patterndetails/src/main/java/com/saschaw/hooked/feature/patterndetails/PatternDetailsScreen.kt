package com.saschaw.hooked.feature.patterndetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.saschaw.hooked.core.designsystem.HookedIcons
import com.saschaw.hooked.core.designsystem.components.ColorStyle
import com.saschaw.hooked.core.designsystem.components.HookedIconButton
import com.saschaw.hooked.core.designsystem.components.ProfilePhoto
import com.saschaw.hooked.core.designsystem.theme.HookedTheme
import com.saschaw.hooked.core.designsystem.R as coreDesignR


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatternDetailsScreen(
    viewModel: PatternDetailsScreenViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) { viewModel.onLoadPattern() }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
    ) {
        AnimatedVisibility(
            visible = uiState == PatternDetailsScreenUiState.Loading,
        ) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        AnimatedVisibility(
            visible = uiState is PatternDetailsScreenUiState.Success,
        ) {
            (uiState as? PatternDetailsScreenUiState.Success)?.patternDetails?.let {
                PatternDetailsScreenLoaded(
                    patternPhotoUrl = it.photos?.firstOrNull()?.mediumUrl,
                    authorName = it.patternAuthor?.name,
                    authorPhotoUrl = it.patternAuthor?.users?.firstOrNull()?.photoUrl,
                    title = it.name,
                    isFavorited = it.personalAttributes?.favorited,
                    price = it.displayPrice,
                    onClickLike = { },
                    onClickPatternRavelryLink = { },
                )
            }
        }

        AnimatedVisibility(
            visible = uiState is PatternDetailsScreenUiState.Error,
        ) {
            Text("NAY")
        }
    }
}

@Composable
fun PatternDetailsScreenLoaded(
    patternPhotoUrl: String?,
    authorName: String?,
    authorPhotoUrl: String?,
    title: String,
    isFavorited: Boolean?,
    onClickLike: () -> Unit,
    onClickPatternRavelryLink: () -> Unit,
    price: String,
) {
    val context = LocalContext.current

    Column {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(patternPhotoUrl)
                    .crossfade(true)
                    .build(),
                fallback = painterResource(coreDesignR.drawable.app_logo),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 250.dp),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                HookedIconButton(
                    onClick = onClickLike,
                    colorStyle = ColorStyle.Tertiary,
                ) {
                    if (isFavorited == true) {
                        Icon(HookedIcons.Favorite, contentDescription = "Remove from likes")
                    } else {
                        Icon(HookedIcons.FavoriteOutlined, contentDescription = "Save to likes")
                    }
                }

                HookedIconButton(
                    onClick = onClickPatternRavelryLink,
                    colorStyle = ColorStyle.Primary,
                ) {
                    Icon(HookedIcons.OpenExternalLink, contentDescription = "Open in browser")
                }
            }
        }
        Column(
            Modifier
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                )

                Text(
                    text = price,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .padding(start = 12.dp, top = 8.dp, bottom = 8.dp)
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(4.dp),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                        )
                        .background(
                            shape = RoundedCornerShape(4.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer,
                        )
                        .padding(4.dp)
                )
            }

            Row(
                modifier = Modifier.padding(8.dp).height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                ProfilePhoto(
                    url = authorPhotoUrl,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    contentDescription = stringResource(coreDesignR.string.author_profile_image_desc)
                )

                Text(
                    text = authorName ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Preview
@Composable
private fun PatternDetailsScreenLoadedPreview() {
    HookedTheme {
        Surface {
            PatternDetailsScreenLoaded(
                patternPhotoUrl = "url.com",
                authorName = "Ravelry Author",
                authorPhotoUrl = "url.com",
                title = "Cool Pattern I Made",
                isFavorited = true,
                price = "$7.99",
                onClickLike = {},
                onClickPatternRavelryLink = {}
            )
        }
    }
}