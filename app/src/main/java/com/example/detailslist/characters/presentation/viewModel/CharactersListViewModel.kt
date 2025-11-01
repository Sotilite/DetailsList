package com.example.detailslist.characters.presentation.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detailslist.CharacterDetails
import com.example.detailslist.CharactersSettings
import com.example.detailslist.characters.data.cashe.BadgeCache
import com.example.detailslist.characters.domain.interactor.CharactersInteractor
import com.example.detailslist.characters.domain.model.CharacterEntity
import com.example.detailslist.characters.presentation.model.CharacterUiModel
import com.example.detailslist.characters.presentation.model.CharactersListFilter
import com.example.detailslist.characters.presentation.model.CharactersListViewState
import com.example.detailslist.core.launchLoadingAndError
import com.example.detailslist.navigation.Route
import com.example.detailslist.navigation.TopLevelBackStack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class CharactersListViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val interactor: CharactersInteractor,
    private val  badgeCache: BadgeCache,
): ViewModel() {
    private val mutableStateOfCharactersListView = MutableStateFlow(CharactersListViewState())
    val viewState = mutableStateOfCharactersListView.asStateFlow()

    private val mutableStateOfBadgeCache = MutableStateFlow(BadgeCache())
    val badgeCacheState = mutableStateOfBadgeCache.asStateFlow()
    //val showBadge: StateFlow<Boolean> = badgeCache.hasActiveFilters

    init {
        loadCharacters()

        viewModelScope.launch {
            interactor.observeAliveFirstSettings()
                .collect { aliveFirst ->
                    //val shouldShowBadge = aliveFirst
                    mutableStateOfBadgeCache.value.setFiltersActivity(aliveFirst)
                }
        }
    }

    fun onCharacterClick(character: CharacterUiModel) {
        topLevelBackStack.add(CharacterDetails(character))
    }

    fun onRetryClick() = loadCharacters()

    fun onSettingsClick() = topLevelBackStack.add(CharactersSettings)

    fun onFilterChange(filter: CharactersListFilter) {
        mutableStateOfCharactersListView.update { it.copy(currentFilter = filter) }
        loadCharacters()
    }

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

            interactor.observeAliveFirstSettings()
                .onEach {
                    updateState(CharactersListViewState.State.Loading)
                }
                .map {
                    if(viewState.value.currentFilter == CharactersListFilter.ALL) {
                        interactor.getCharacters(it)
                    } else {
                        interactor.getFavorites()
                    }
                }
                .collect { characters ->
                    updateState(CharactersListViewState.State.Success(mapToUi(characters)))
                }
        }
    }

    private fun updateState(state: CharactersListViewState.State) =
        mutableStateOfCharactersListView.update { it.copy(listState = state) }

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