package com.example.detailslist.characters.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.detailslist.characters.presentation.model.CharacterDetailsViewState
import com.example.detailslist.characters.presentation.model.CharacterUiModel
import com.example.detailslist.characters.presentation.viewModel.CharacterDetailsViewModel
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
    ConstraintLayout(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 12.dp)
    ) {
        val (image, name, species, gender, status, origin, info) = createRefs()

        GlideImage(
            model = state.character.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
        )
        Text(
            text = state.character.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.constrainAs(name) {
                top.linkTo(parent.top)
                absoluteLeft.linkTo(image.absoluteRight, 8.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = String.format("Вид: %s", state.character.species),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.constrainAs(species) {
                top.linkTo(name.bottom)
                absoluteLeft.linkTo(image.absoluteRight, 8.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = String.format("Пол: %s", state.character.gender),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.constrainAs(gender) {
                top.linkTo(species.bottom)
                absoluteLeft.linkTo(image.absoluteRight, 8.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = String.format("Статус: %s", state.character.status),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.constrainAs(status) {
                top.linkTo(gender.bottom)
                absoluteLeft.linkTo(image.absoluteRight, 8.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = String.format("Происхождение: %s", state.character.origin),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.constrainAs(origin) {
                top.linkTo(status.bottom)
                absoluteLeft.linkTo(image.absoluteRight, 8.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = String.format("Краткая информация. %s", state.character.info),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.constrainAs(info) {
                top.linkTo(image.bottom, 8.dp)
            }
        )
    }
}
