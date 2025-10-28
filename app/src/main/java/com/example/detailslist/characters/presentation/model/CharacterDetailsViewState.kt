package com.example.detailslist.characters.presentation.model

data class CharacterDetailsViewState(
    val character: CharacterUiModel
) {
    val speciesFormattedString get() = String.format("Вид: %s", character.species)
    val genderFormattedString get() = String.format("Пол: %s", character.gender)
    val statusFormattedString get() = String.format("Статус: %s", character.status)
    val originFormattedString get() = String.format("Место рождения: %s", character.origin)
    val locationFormattedString get() = String.format("Местонахождение: %s", character.location)
}
