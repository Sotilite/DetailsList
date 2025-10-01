package com.example.detailslist.characters.presentation

import com.example.detailslist.characters.presentation.model.CharactersUiModel

object MockData {
    fun getCharacters(): List<CharactersUiModel> = listOf(
        CharactersUiModel(
            id = "1",
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            origin = "Earth (C-137)",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        ),
        CharactersUiModel(
            id = "2",
            name = "Morty Smith",
            status = "Alive",
            species = "Human",
            gender = "Male",
            origin = "unknown",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
        ),
        CharactersUiModel(
            id = "3",
            name = "Summer Smith",
            status = "Alive",
            species = "Human",
            gender = "Female",
            origin = "Earth (Replacement Dimension)",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/3.jpeg"
        ),
        CharactersUiModel(
            id = "4",
            name = "Beth Smith",
            status = "Alive",
            species = "Human",
            gender = "Female",
            origin = "Earth (Replacement Dimension)",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/4.jpeg"
        ),
        CharactersUiModel(
            id = "5",
            name = "Jerry Smith",
            status = "Alive",
            species = "Human",
            gender = "Male",
            origin = "Earth (Replacement Dimension)",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/5.jpeg"
        ),
        CharactersUiModel(
            id = "6",
            name = "Abadango Cluster Princess",
            status = "Alive",
            species = "Alien",
            gender = "Female",
            origin = "Abadango",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/6.jpeg"
        ),
        CharactersUiModel(
            id = "7",
            name = "Abradolf Lincler",
            status = "unknown",
            species = "Human",
            gender = "Male",
            origin = "Earth (Replacement Dimension)",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/7.jpeg"
        ),
        CharactersUiModel(
            id = "8",
            name = "Adjudicator Rick",
            status = "Dead",
            species = "Human",
            gender = "Male",
            origin = "unknown",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/8.jpeg"
        ),
        CharactersUiModel(
            id = "9",
            name = "Agency Director",
            status = "Dead",
            species = "Human",
            gender = "Male",
            origin = "Earth (Replacement Dimension)",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/9.jpeg"
        ),
        CharactersUiModel(
            id = "10",
            name = "Alan Rails",
            status = "Dead",
            species = "Human",
            gender = "Male",
            origin = "unknown",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/10.jpeg"
        )
    )
}
