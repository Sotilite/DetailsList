package com.example.detailslist.characters.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.detailslist.characters.data.mapper.CharactersResponseToEntityMapper
import com.example.detailslist.characters.data.api.CharactersApi
import com.example.detailslist.characters.data.db.CharactersDatabase
import com.example.detailslist.characters.data.entity.CharacterDbEntity
import com.example.detailslist.characters.domain.model.CharacterEntity
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharactersRepository(
    private val api: CharactersApi,
    private val mapper: CharactersResponseToEntityMapper,
    private val dataStore: DataStore<Preferences>,
    private val db: CharactersDatabase,
) {
    private val aliveFirstKey = booleanPreferencesKey(ALIVE_FIRST_KEY)

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getCharacters(aliveFirst: Boolean) = withContext(Dispatchers.IO) {
        val response = api.getCharacters()
        val characters = mapper.mapResponse(response)

        if(aliveFirst) {
            characters?.sortedBy { it.status != "Alive" }
        } else {
            characters
        }
    }

    suspend fun setAliveFirstSettings(aliveFirst: Boolean) = withContext(Dispatchers.IO) {
        dataStore.edit {
            it[aliveFirstKey] = aliveFirst
        }
    }

    fun observeAliveFirstSettings(): Flow<Boolean> =
        dataStore.data.map { it[aliveFirstKey] ?: false }

    suspend fun isFavorite(name: String): Boolean {
        return db.characterDao().getCharacter(name) != null
    }

    suspend fun getFavorites() =
        withContext(Dispatchers.IO) {
            db.characterDao().getAll().map {
                CharacterEntity(
                    id = it.id.toString(),
                    name = it.name,
                    status = it.status,
                    species = it.species,
                    gender = it.gender,
                    origin = it.origin,
                    location = it.location,
                    imageUrl = it.imageUrl,
                    creationTime = it.creationTime
                )
            }
        }

    suspend fun saveFavorite(character: CharacterEntity) =
        withContext(Dispatchers.IO) {
            db.characterDao().insert(
                CharacterDbEntity(
                    name = character.name,
                    status = character.status,
                    species = character.species,
                    gender = character.gender,
                    origin = character.origin,
                    location = character.location,
                    imageUrl = character.imageUrl,
                    creationTime = character.creationTime
                )
            )
        }

    suspend fun deleteFromFavorites(characterName: String) =
        withContext(Dispatchers.IO) {
        db.characterDao().deleteByName(characterName)
    }

    companion object {
        private const val ALIVE_FIRST_KEY = "ALIVE_FIRST_KEY"
    }
}