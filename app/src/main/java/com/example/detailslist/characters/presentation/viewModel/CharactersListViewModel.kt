package com.example.detailslist.characters.presentation.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detailslist.CharacterDetails
import com.example.detailslist.characters.domain.interactor.CharactersInteractor
import com.example.detailslist.characters.domain.model.CharacterEntity
import com.example.detailslist.characters.presentation.model.CharacterUiModel
import com.example.detailslist.characters.presentation.model.CharactersListViewState
import com.example.detailslist.core.launchLoadingAndError
import com.example.detailslist.navigation.Route
import com.example.detailslist.navigation.TopLevelBackStack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@RequiresApi(Build.VERSION_CODES.O)
class CharactersListViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val interactor: CharactersInteractor,
): ViewModel() {
    private val mutableState = MutableStateFlow(CharactersListViewState())
    val viewState = mutableState.asStateFlow()

    init {
        loadCharacters()
    }

    fun onCharacterClick(character: CharacterUiModel) {
        topLevelBackStack.add(CharacterDetails(character))
    }

    fun onRetryClick() = loadCharacters()

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadCharacters() {
        viewModelScope.launchLoadingAndError(
            handleError = { e ->
                updateState(CharactersListViewState.State.Error(
                    e.localizedMessage.orEmpty())
                )
            }
        ) {
            updateState(CharactersListViewState.State.Loading)

            val characters = interactor.getCharacters()
            updateState(CharactersListViewState.State.Success(mapToUi(characters)))
        }
    }

    private fun updateState(state: CharactersListViewState.State) =
        mutableState.update { it.copy(state = state) }

    private fun mapToUi(characters: List<CharacterEntity>?): List<CharacterUiModel> =
        characters?.map { character ->
            CharacterUiModel(
                id = character.id,
                name = character.name,
                status = character.status,
                species = character.species,
                gender = character.gender,
                origin = character.origin,
                location = character.location,
                imageUrl = character.imageUrl,
                creationTime = character.creationTime.toString()
            )
        }.orEmpty()
}