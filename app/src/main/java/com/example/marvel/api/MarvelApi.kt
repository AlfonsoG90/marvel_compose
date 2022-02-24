package com.example.marvel.api

import com.example.marvel.api.models.characterList.CharacterListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    suspend fun fechtCharacters(@Query("limit") limit:Int) : CharacterListResponse

    @GET("characters/{characterId}")
    suspend fun getCharacter(@Path("characterId")  characterId:String) : CharacterListResponse
}