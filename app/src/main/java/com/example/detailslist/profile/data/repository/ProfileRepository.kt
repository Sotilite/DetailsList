package com.example.detailslist.profile.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import com.example.detailslist.profile.data.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDateTime
import java.time.LocalTime

class ProfileRepository {
    private val dataStore : DataStore<ProfileEntity> by inject(DataStore::class.java, named("profile"))

    suspend fun observeProfile(): Flow<ProfileEntity> = dataStore.data

    suspend fun getProfile(): ProfileEntity? = dataStore.data.firstOrNull()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun setProfile(photoUri: String, name: String, url: String, time: LocalTime = LocalTime.now()) =
        dataStore.updateData {
            it.toBuilder().apply {
                this.photoUri = photoUri
                this.name = name
                this.url = url
                this.time = time.toString()
            }.build()
        }
}