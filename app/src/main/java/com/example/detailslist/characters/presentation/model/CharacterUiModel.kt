package com.example.detailslist.characters.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterUiModel(
    val id: String,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: String,
    val location: String,
    val imageUrl: String?,
    val creationTime: String
)
