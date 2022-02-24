package com.example.marvel.api

import com.example.marvel.BuildConfig
import com.example.marvel.api.models.characterList.CharacterListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MarvelRepository {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val marvelApi: MarvelApi by lazy {
        val httpClient = OkHttpClient.Builder().addInterceptor(MarvelInterceptor()).addInterceptor(
            loggingInterceptor
        )
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
        return@lazy retrofit.create(MarvelApi::class.java)
    }

    fun fetchCharacters(limit: Int): Flow<CharacterListResponse> = flow {
        emit(marvelApi.fechtCharacters(limit))
    }

    fun getCharacter(characterId: String): Flow<CharacterListResponse> = flow {
        emit(marvelApi.getCharacter(characterId))
    }
}