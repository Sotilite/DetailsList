package com.example.profile.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import com.example.profile.data.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject

class ProfileRepository {
    private val dataStore : DataStore<ProfileEntity> by inject(DataStore::class.java, named("profile"))

    suspend fun observeProfile(): Flow<ProfileEntity> = dataStore.data

    suspend fun getProfile(): ProfileEntity? = dataStore.data.firstOrNull()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun setProfile(photoUri: String, name: String,
                           url: String, favoriteLessonTime: String) =
        dataStore.updateData {
            it.toBuilder().apply {
                this.photoUri = photoUri
                this.name = name
                this.url = url
                this.favoriteLessonTime = favoriteLessonTime
            }.build()
        }
}