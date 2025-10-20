package com.example.detailslist.characters.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.detailslist.characters.presentation.model.CharacterDetailsViewState
import com.example.detailslist.characters.presentation.model.CharacterUiModel
import com.example.detailslist.characters.presentation.viewModel.CharacterDetailsViewModel
import com.example.detailslist.uikit.Spacing
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsView(character: CharacterUiModel) {
    val viewModel = koinViewModel<CharacterDetailsViewModel> {
        parametersOf(character)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .background(Color.DarkGray),
        topBar = {
            TopAppBar(
                title = { Text("Персонажи") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            viewModel.onBack()
                        },
                    )
                }
            )
        }
    ) { padding ->
        CharacterDetailsContent(
            state = state,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterDetailsContent(state: CharacterDetailsViewState, modifier: Modifier = Modifier) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .wrapContentSize(Alignment.Center)
    ){
        Text(
            modifier = Modifier
                .padding(0.dp,Spacing.mini),
            text = state.character.name,
            style = MaterialTheme.typography.headlineLarge,
        )
        Text(
            modifier = Modifier
                .padding(0.dp,Spacing.mini),
            text = state.character.creationTime,
            style = MaterialTheme.typography.labelSmall
        )
        GlideImage(
            model = state.character.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(350.dp, )
                .clip(RoundedCornerShape(16.dp))
                .padding(Spacing.mini)
        )

        Text(
            modifier = Modifier
                .padding(0.dp, Spacing.mini),
            text = state.speciesFormattedString,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            modifier = Modifier
                .padding(0.dp,Spacing.mini),
            text = state.genderFormattedString,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            modifier = Modifier
                .padding(0.dp,Spacing.mini),
            text = state.statusFormattedString,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            modifier = Modifier
                .padding(0.dp,Spacing.mini),
            text = state.originFormattedString,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            modifier = Modifier
                .padding(0.dp,Spacing.mini),
            text = state.locationFormattedString,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
