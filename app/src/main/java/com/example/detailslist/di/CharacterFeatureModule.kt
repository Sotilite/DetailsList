package com.example.detailslist.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.detailslist.characters.data.mapper.CharactersResponseToEntityMapper
import com.example.detailslist.characters.data.api.CharactersApi
import com.example.detailslist.characters.data.repository.CharactersRepository
import com.example.detailslist.characters.domain.interactor.CharactersInteractor
import com.example.detailslist.characters.presentation.viewModel.CharacterDetailsViewModel
import com.example.detailslist.characters.presentation.viewModel.CharactersListViewModel
import com.example.detailslist.characters.presentation.viewModel.CharactersSettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

@RequiresApi(Build.VERSION_CODES.O)
val characterFeatureModule = module {
    viewModel { CharacterDetailsViewModel(get(), get(), get()) }
    viewModel { CharactersListViewModel(get(), get(), get()) }
    viewModel { CharactersSettingsViewModel(get(), get()) }

    single { get<Retrofit>().create(CharactersApi::class.java) }

    factory { CharactersResponseToEntityMapper() }
    single { CharactersRepository(get(), get(), get(), get()) }

    single { CharactersInteractor(get()) }
}