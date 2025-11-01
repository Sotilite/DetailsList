package com.example.detailslist.characters.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detailslist.characters.data.cashe.BadgeCache
import com.example.detailslist.characters.domain.interactor.CharactersInteractor
import com.example.detailslist.characters.presentation.model.CharactersSettingsState
import com.example.detailslist.navigation.Route
import com.example.detailslist.navigation.TopLevelBackStack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersSettingsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val interactor: CharactersInteractor,
): ViewModel() {
    private val mutableState = MutableStateFlow(CharactersSettingsState())
    val viewState = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            interactor.observeAliveFirstSettings().collect { aliveFirst ->
                mutableState.update { it.copy(aliveFirst = aliveFirst) }
            }
        }
    }

    fun onAliveFirstCheckedChange(isChecked: Boolean) {
        mutableState.update { it.copy(aliveFirst = isChecked) }
    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            interactor.setAliveFirstSettings(viewState.value.aliveFirst)
            onBack()
        }
    }
}