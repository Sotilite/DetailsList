package com.example.detailslist.characters.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class CharacterDetailsViewState(
    val character: CharacterUiModel,
    val isFavorite: Boolean = false,
) {
    val speciesFormattedString get() = String.format("Вид: %s", character.species)
    val genderFormattedString get() = String.format("Пол: %s", character.gender)
    val statusFormattedString get() = String.format("Статус: %s", character.status)
    val originFormattedString get() = String.format("Место рождения: %s", character.origin)
    val locationFormattedString get() = String.format("Местонахождение: %s", character.location)

    val favoriteIcon get() = if (isFavorite)  Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
}
