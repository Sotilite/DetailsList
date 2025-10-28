package com.example.detailslist.characters.domain.model

import com.example.detailslist.characters.data.model.Location
import java.time.LocalDateTime

class CharacterEntity(
    val id: String,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: String,
    val location: String,
    val imageUrl: String?,
    val creationTime: LocalDateTime
)