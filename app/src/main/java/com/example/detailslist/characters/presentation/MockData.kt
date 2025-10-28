package com.example.detailslist.characters.presentation

import com.example.detailslist.characters.presentation.model.CharacterUiModel

object MockData {
    fun getCharacters(): List<CharacterUiModel> = listOf(
        CharacterUiModel(
            id = "1",
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            origin = "Earth (C-137)",
            location = "Citadel of Ricks",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            creationTime = "2017-11-04T18:48:46.250Z"
        ),
        CharacterUiModel(
            id = "2",
            name = "Morty Smith",
            status = "Alive",
            species = "Human",
            gender = "Male",
            origin = "unknown",
            location = "Citadel of Ricks",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            creationTime = "2017-11-04T18:50:21.651Z"
        ),
        CharacterUiModel(
            id = "3",
            name = "Summer Smith",
            status = "Alive",
            species = "Human",
            gender = "Female",
            origin = "Earth (Replacement Dimension)",
            location = "Earth (Replacement Dimension)",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
            creationTime = "2017-11-04T19:09:56.428Z"
        ),
        CharacterUiModel(
            id = "4",
            name = "Beth Smith",
            status = "Alive",
            species = "Human",
            gender = "Female",
            origin = "Earth (Replacement Dimension)",
            location = "Earth (Replacement Dimension)",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/4.jpeg",
            creationTime = "2017-11-04T19:22:43.665Z"
        ),
        CharacterUiModel(
            id = "5",
            name = "Jerry Smith",
            status = "Alive",
            species = "Human",
            gender = "Male",
            origin = "Earth (Replacement Dimension)",
            location = "Earth (Replacement Dimension)",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/5.jpeg",
            creationTime = "2017-11-04T19:26:56.301Z"
        ),
        CharacterUiModel(
            id = "6",
            name = "Abadango Cluster Princess",
            status = "Alive",
            species = "Alien",
            gender = "Female",
            origin = "Abadango",
            location = "Abadango",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/6.jpeg",
            creationTime = "2017-11-04T19:50:28.250Z"
        ),
        CharacterUiModel(
            id = "7",
            name = "Abradolf Lincler",
            status = "unknown",
            species = "Human",
            gender = "Male",
            origin = "Earth (Replacement Dimension)",
            location = "Earth (Replacement Dimension)",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/7.jpeg",
            creationTime = "2017-11-04T19:59:20.523Z"
        ),
        CharacterUiModel(
            id = "8",
            name = "Adjudicator Rick",
            status = "Dead",
            species = "Human",
            gender = "Male",
            origin = "unknown",
            location = "Citadel of Ricks",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/8.jpeg",
            creationTime = "2017-11-04T20:03:34.737Z"
        ),
        CharacterUiModel(
            id = "9",
            name = "Agency Director",
            status = "Dead",
            species = "Human",
            gender = "Male",
            origin = "Earth (Replacement Dimension)",
            location = "Earth (Replacement Dimension)",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/9.jpeg",
            creationTime = "2017-11-04T20:06:54.976Z"
        ),
        CharacterUiModel(
            id = "10",
            name = "Alan Rails",
            status = "Dead",
            species = "Human",
            gender = "Male",
            origin = "unknown",
            location = "Worldender's lair",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/10.jpeg",
            creationTime = "2017-11-04T20:19:09.017Z"
        )
    )
}
