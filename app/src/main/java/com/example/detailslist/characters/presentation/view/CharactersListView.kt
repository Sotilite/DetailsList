package com.example.detailslist.characters.presentation.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.detailslist.characters.data.cashe.BadgeCache
import com.example.detailslist.characters.presentation.model.CharacterUiModel
import com.example.detailslist.characters.presentation.model.CharactersListFilter
import com.example.detailslist.characters.presentation.model.CharactersListViewState
import com.example.detailslist.characters.presentation.viewModel.CharactersListViewModel
import com.example.detailslist.uikit.FullScreenError
import com.example.detailslist.uikit.FullscreenLoading
import com.example.detailslist.uikit.Spacing
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CharactersListView() {
    val viewModel = koinViewModel<CharactersListViewModel>()
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val badgeCacheState by viewModel.badgeCacheState.collectAsStateWithLifecycle()

    CharactersListViewContent(
        state,
        viewModel::onCharacterClick,
        viewModel::onRetryClick,
        viewModel::onSettingsClick,
        viewModel::onFilterChange,
        badgeCacheState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharactersListViewContent(
    state: CharactersListViewState,
    onCharacterClick: (CharacterUiModel) -> Unit = {},
    onRetryClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onFilterChange: (CharactersListFilter) -> Unit = {},
    badgeCacheState: BadgeCache,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        floatingActionButton = {
            BadgedBox(
                badge = {
                    if (badgeCacheState.hasActiveFilters()) {
                        Badge(
                            containerColor = Color.Red,
                            modifier = Modifier.size(8.dp)
                        )
                    }
                }
            ) {
                FloatingActionButton(onClick = { onSettingsClick() }) {
                    Icon(
                        Icons.Default.Settings,
                        "Settings button"
                    )
                }
            }
        },
        topBar = {
            TopAppBar(
                title = { CharactersListFilters(state, onFilterChange) },
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        Box(
            modifier = Modifier
                .padding(it),
        ) {
            when(state.listState) {
                CharactersListViewState.State.Loading -> {
                    FullscreenLoading()
                }

                is CharactersListViewState.State.Error -> {
                    FullScreenError(
                        retry = { onRetryClick() },
                        text = state.listState.error
                    )
                }

                is CharactersListViewState.State.Success -> {
                    LazyColumn {
                        state.listState.data.forEach { character ->
                            item(key = character.id) {
                                CharactersListItem(character) { onCharacterClick(it) }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CharactersListItem(character: CharacterUiModel, onCharacterClick: (CharacterUiModel) -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onCharacterClick(character) }
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row {
            GlideImage(
                model = character.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit,
            )
            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = String.format("Status: %s", character.status),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = String.format("Origin: %s", character.origin),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        HorizontalDivider()
    }
}

@Composable
private fun CharactersListFilters(
    state: CharactersListViewState,
    onFilterChange: (CharactersListFilter) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement
            .spacedBy(Spacing.small),
    ) {
        state.filters.forEach { filter ->
            FilterChip(
                selected = filter == state.currentFilter,
                label = { Text(filter.text) },
                onClick = { onFilterChange(filter) },
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CharactersListPreview() {
    CharactersListView()
}