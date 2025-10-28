package com.example.detailslist.characters.domain.interactor

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.detailslist.characters.data.repository.CharactersRepository

class CharactersInteractor(
    private val repository: CharactersRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getCharacters() = repository.getCharacters()
}