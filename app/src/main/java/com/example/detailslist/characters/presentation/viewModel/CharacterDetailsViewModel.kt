package com.example.detailslist.characters.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.detailslist.characters.presentation.model.CharacterDetailsViewState
import com.example.detailslist.characters.presentation.model.CharacterUiModel
import com.example.detailslist.navigation.Route
import com.example.detailslist.navigation.TopLevelBackStack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterDetailsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val character: CharacterUiModel
): ViewModel() {
    private val mutableState = MutableStateFlow(CharacterDetailsViewState(character))
    val state = mutableState.asStateFlow()

    fun onBack() {
        topLevelBackStack.removeLast()
    }
}