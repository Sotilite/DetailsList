package com.example.detailslist.characters.presentation.viewModel

import androidx.lifecycle.ViewModel
//import androidx.room.util.copy
import com.example.detailslist.CharacterDetails
import com.example.detailslist.characters.presentation.MockData
import com.example.detailslist.characters.presentation.model.CharacterUiModel
import com.example.detailslist.characters.presentation.model.CharactersListViewState
import com.example.detailslist.navigation.Route
import com.example.detailslist.navigation.TopLevelBackStack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CharactersListViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
): ViewModel() {
    private val mutableState = MutableStateFlow(CharactersListViewState())
    val viewState = mutableState.asStateFlow()

    init {
        mutableState.update {
            it.copy(
                state = CharactersListViewState.State.Success(MockData.getCharacters())
            )
        }
    }

    fun onCharacterClick(character: CharacterUiModel) {
        topLevelBackStack.add(CharacterDetails(character))
    }
}