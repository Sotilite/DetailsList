package com.example.detailslist.characters.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.detailslist.characters.data.mapper.CharactersResponseToEntityMapper
import com.example.detailslist.characters.data.CharactersApi
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

class CharactersRepository(
    private val api: CharactersApi,
    private val mapper: CharactersResponseToEntityMapper,
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getCharacters() = withContext(Dispatchers.IO) {
        val response = api.getCharacters()
        mapper.mapResponse(response)
    }
}