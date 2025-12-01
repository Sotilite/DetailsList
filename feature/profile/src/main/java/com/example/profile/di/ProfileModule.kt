package com.example.profile.di

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import com.example.core.navigation.EntryProviderInstaller
import com.example.core.navigation.Route
import com.example.core.navigation.TopLevelRoute
import com.example.profile.data.entity.ProfileEntity
import com.example.profile.data.provider.DataSourceProvider
import com.example.profile.data.repository.ProfileRepository
import com.example.profile.presentation.view.EditProfileView
import com.example.profile.presentation.view.ProfileView
import com.example.profile.presentation.viewModel.EditProfileViewModel
import com.example.profile.presentation.viewModel.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val PROFILE_QUALIFIER = "profileQualifier"

data object Profile : TopLevelRoute {
    override val icon = Icons.Default.Face
}

data object EditProfile : Route

val profileModule = module {
    factory<DataStore<ProfileEntity>>(named("profile")) { DataSourceProvider(get()).provide() }
    single<ProfileRepository> { ProfileRepository() }

    viewModel { ProfileViewModel(get()) }
    viewModel { EditProfileViewModel(get(), get()) }

    factory<EntryProviderInstaller>(qualifier = named(PROFILE_QUALIFIER)) {
        {
          entry<Profile> {
              ProfileView().Content(Modifier.fillMaxWidth())
          }
          entry<EditProfile> {
              EditProfileView().Content(Modifier.fillMaxWidth())
          }
        }
    }
}
