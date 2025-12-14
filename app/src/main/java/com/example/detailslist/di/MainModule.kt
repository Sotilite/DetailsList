package com.example.detailslist.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.detailslist.Characters
import com.example.detailslist.characters.data.cashe.BadgeCache
import com.example.core.navigation.TopLevelBackStack
import com.example.core.navigation.Route
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module {
    single {
        TopLevelBackStack<Route>(Characters)
    }
    single {
        getDataStore(androidContext())
    }
    single {
        BadgeCache()
    }
}

fun getDataStore(androidContext: Context): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create {
        androidContext.preferencesDataStoreFile("default")
    }
}
