package com.example.detailslist.di

import com.example.detailslist.characters.presentation.viewModel.CharacterDetailsViewModel
import com.example.detailslist.characters.presentation.viewModel.CharactersListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val characterFeatureModule = module {
    viewModel { CharacterDetailsViewModel(get(), get()) }
    viewModel { CharactersListViewModel(get()) }
}