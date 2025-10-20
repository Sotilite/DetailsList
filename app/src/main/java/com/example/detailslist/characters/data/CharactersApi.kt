package com.example.detailslist.characters.data

import com.example.detailslist.characters.data.model.CharactersListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApi {
    @GET("character")
    suspend fun getCharacters(
        @Query("orderBy") orderBy: String = CREATE_TIME_KEY
    ): CharactersListResponse

    companion object {
        private const val CREATE_TIME_KEY = "createTime desc"
    }
}