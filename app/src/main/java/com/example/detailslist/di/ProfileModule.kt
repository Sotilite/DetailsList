package com.example.detailslist.di

import androidx.datastore.core.DataStore
import com.example.detailslist.profile.data.entity.ProfileEntity
import com.example.detailslist.profile.data.provider.DataSourceProvider
import com.example.detailslist.profile.data.repository.ProfileRepository
import com.example.detailslist.profile.presentation.viewModel.EditProfileViewModel
import com.example.detailslist.profile.presentation.viewModel.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val profileModule = module {
    factory<DataStore<ProfileEntity>>(named("profile")) { DataSourceProvider(get()).provide() }
    single<ProfileRepository> { ProfileRepository() }

    viewModel { ProfileViewModel(get()) }
    viewModel { EditProfileViewModel(get(), get()) }
}