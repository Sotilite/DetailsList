package com.example.detailslist.characters.domain.interactor

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.detailslist.characters.data.repository.CharactersRepository
import com.example.detailslist.characters.domain.model.CharacterEntity

class CharactersInteractor(
    private val repository: CharactersRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getCharacters(aliveFirst: Boolean) = repository.getCharacters(aliveFirst)

    fun observeAliveFirstSettings() = repository.observeAliveFirstSettings()

    suspend fun setAliveFirstSettings(aliveFirst: Boolean) =
        repository.setAliveFirstSettings(aliveFirst)

    suspend fun isFavorite(name: String): Boolean {
        return repository.isFavorite(name)
    }

    suspend fun saveFavorite(character: CharacterEntity) = repository.saveFavorite(character)

    suspend fun deleteFromFavorites(characterName: String) = repository.deleteFromFavorites(characterName)

    suspend fun getFavorites() = repository.getFavorites()
}