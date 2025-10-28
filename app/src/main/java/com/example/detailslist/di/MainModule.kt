package com.example.detailslist.di

import com.example.detailslist.Characters
import com.example.detailslist.characters.presentation.viewModel.CharacterDetailsViewModel
import com.example.detailslist.navigation.TopLevelBackStack
import com.example.detailslist.navigation.Route
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { TopLevelBackStack<Route>(Characters) }
}
