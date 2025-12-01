package com.example.detailslist.characters.presentation.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detailslist.characters.domain.interactor.CharactersInteractor
import com.example.detailslist.characters.domain.model.CharacterEntity
import com.example.detailslist.characters.presentation.model.CharacterDetailsViewState
import com.example.detailslist.characters.presentation.model.CharacterUiModel
import com.example.core.navigation.Route
import com.example.core.navigation.TopLevelBackStack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val character: CharacterUiModel,
    private val interactor: CharactersInteractor,
): ViewModel() {
    private val mutableState = MutableStateFlow(CharacterDetailsViewState(character))
    val state = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            val isFavorite = interactor.isFavorite(character.name)
            mutableState.update { it.copy(isFavorite = isFavorite) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onFavoriteChange() {
        viewModelScope.launch {
            val currentIsFavorite = !state.value.isFavorite
            mutableState.update {
                it.copy(isFavorite = currentIsFavorite)
            }
            if(currentIsFavorite){
                interactor.saveFavorite(
                    CharacterEntity(
                        id = character.id,
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
            } else {
                interactor.deleteFromFavorites(character.name)
            }
        }
    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }
}