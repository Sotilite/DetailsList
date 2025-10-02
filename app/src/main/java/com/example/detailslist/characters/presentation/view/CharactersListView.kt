package com.example.detailslist.characters.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.detailslist.CharacterDetails
import com.example.detailslist.Characters
import com.example.detailslist.characters.presentation.MockData
import com.example.detailslist.characters.presentation.model.CharacterUiModel
import com.example.detailslist.navigation.Route
import com.example.detailslist.navigation.TopLevelBackStack

@Composable
fun CharactersListView(topLevelBackStack: TopLevelBackStack<Route>) {
    val characters = remember { MockData.getCharacters() }

    LazyColumn {
        characters.forEach { character ->
            item(key = character.id) {
                CharactersListItem(
                    character,
                    onCharacterClick = {
                        topLevelBackStack.add(CharacterDetails(character))
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharactersListItem(character: CharacterUiModel, onCharacterClick: (CharacterUiModel) -> Unit) {
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

@Preview(showBackground = true)
@Composable
fun CharactersListPreview() {
    CharactersListView(TopLevelBackStack(Characters))
}