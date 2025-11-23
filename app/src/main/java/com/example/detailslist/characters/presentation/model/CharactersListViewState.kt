package com.example.detailslist.characters.presentation.model

data class CharactersListViewState(
    val listState: State = State.Loading,
    val filters: List<CharactersListFilter> = CharactersListFilter.entries,
    var currentFilter: CharactersListFilter = CharactersListFilter.ALL,
) {
    sealed interface State {
        object Loading : State
        data class  Error(val error: String) : State
        data class  Success(val data: List<CharacterUiModel>) : State
    }
}

enum class CharactersListFilter(val text: String) {
    ALL("Все"),
    FAVORITES("Избранные"),
}