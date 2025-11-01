package com.example.detailslist.characters.domain.model

class CharacterEntity(
    val id: String,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: String,
    val location: String,
    val imageUrl: String?,
    val creationTime: String?
)