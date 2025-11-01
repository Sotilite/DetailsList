package com.example.detailslist.characters.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.detailslist.characters.data.model.CharactersListResponse
import com.example.detailslist.characters.domain.model.CharacterEntity

class CharactersResponseToEntityMapper {
    @RequiresApi(Build.VERSION_CODES.O)
    fun mapResponse(response: CharactersListResponse): List<CharacterEntity>? {
        return response.results?.map { res ->
            CharacterEntity(
                id = res.id.toString(),
                name = res.name.orEmpty(),
                status = res.status.orEmpty(),
                species = res.species.orEmpty(),
                gender = res.gender.orEmpty(),
                origin = res.origin?.name.orEmpty(),
                location = res.location?.name.orEmpty(),
                imageUrl = res.image,
                creationTime = res.created,
            )
        }.orEmpty()
    }
}