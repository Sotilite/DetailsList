package com.example.detailslist.characters.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
class CharactersListResponse(
    val results: List<CharactersListResult>?,
)

@Keep
@Serializable
class CharactersListResult(
    val id: Int?,
    val name: String?,
    val status: String?,
    val species: String?,
    val gender: String?,
    val origin: Origin?,
    val location: Location?,
    val created: String?,
    val image: String?,
)

@Keep
@Serializable
class Origin(
    val name: String,
    val url: String
)

@Keep
@Serializable
class Location(
    val name: String,
    val url: String
)