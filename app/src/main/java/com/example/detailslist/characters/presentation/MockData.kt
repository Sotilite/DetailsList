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
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            info = "Гениальный ученый с алкогольной зависимостью. Изобретатель портальной пушки и дед Морти. Известен своим циничным отношением к жизни и любовью к приключениям."
        ),
        CharacterUiModel(
            id = "2",
            name = "Morty Smith",
            status = "Alive",
            species = "Human",
            gender = "Male",
            origin = "unknown",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            info = "14-летний внук Рика Санчеза. Часто испытывает страх и тревогу во время приключений, но проявляет храбрость когда это необходимо. Носит желтую футболку."
        ),
        CharacterUiModel(
            id = "3",
            name = "Summer Smith",
            status = "Alive",
            species = "Human",
            gender = "Female",
            origin = "Earth (Replacement Dimension)",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
            info = "Старшая сестра Морти, 17 лет. Типичный подросток, озабоченный социальным статусом и популярностью. Со временем начинает участвовать в приключениях Рика."
        ),
        CharacterUiModel(
            id = "4",
            name = "Beth Smith",
            status = "Alive",
            species = "Human",
            gender = "Female",
            origin = "Earth (Replacement Dimension)",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/4.jpeg",
            info = "Дочь Рика, мать Морти и Саммер, работает кардиохирургом-ветеринаром. Испытывает сложные чувства к отцу из-за его долгого отсутствия в её жизни."
        ),
        CharacterUiModel(
            id = "5",
            name = "Jerry Smith",
            status = "Alive",
            species = "Human",
            gender = "Male",
            origin = "Earth (Replacement Dimension)",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/5.jpeg",
            info = "Муж Бет и отец Морти и Саммер. Работает в рекламном агентстве. Известен своей неуверенностью в себе и ревностью к Рику."
        ),
        CharacterUiModel(
            id = "6",
            name = "Abadango Cluster Princess",
            status = "Alive",
            species = "Alien",
            gender = "Female",
            origin = "Abadango",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/6.jpeg",
            info = "Принцесса из скопления Абаданго. Встречается в эпизоде, где Морти спасает её от тюрьмы. Имеет розовую кожу и королевские регалии."
        ),
        CharacterUiModel(
            id = "7",
            name = "Abradolf Lincler",
            status = "unknown",
            species = "Human",
            gender = "Male",
            origin = "Earth (Replacement Dimension)",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/7.jpeg",
            info = "Генетический гибрид Авраама Линкольна и Адольфа Гитлера, созданный Риком. Страдает от внутреннего конфликта между добром и злом."
        ),
        CharacterUiModel(
            id = "8",
            name = "Adjudicator Rick",
            status = "Dead",
            species = "Human",
            gender = "Male",
            origin = "unknown",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/8.jpeg",
            info = "Член Совета Риков, выполняющий судебные функции. Был убит после попытки арестовать нашего Рика за нарушение правил мультивселенной."
        ),
        CharacterUiModel(
            id = "9",
            name = "Agency Director",
            status = "Dead",
            species = "Human",
            gender = "Male",
            origin = "Earth (Replacement Dimension)",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/9.jpeg",
            info = "Директор Агентства по борьбе с инопланетянами. Был убит Риком после того, как агентство попыталось захватить его технологии."
        ),
        CharacterUiModel(
            id = "10",
            name = "Alan Rails",
            status = "Dead",
            species = "Human",
            gender = "Male",
            origin = "unknown",
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/10.jpeg",
            info = "Супергерой, способный призывать призрачные поезда. Погиб в бою с Звездным Змеем во время миссии по спасению мира."
        )
    )
}
