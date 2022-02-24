package com.example.marvel.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.marvel.api.MarvelRepository
import com.example.marvel.api.models.characterList.CharacterListResponse
import com.example.marvel.ui.base.ViewModelBase
import com.example.marvel.utils.extension.useLoading
import kotlinx.coroutines.flow.catch

class MainViewModel : ViewModelBase() {
    private val limit = 70

    val fetchCharacter: LiveData<CharacterListResponse> =
        MarvelRepository.fetchCharacters(limit)
            .useLoading(this)
            .catch { onRetrievePostListError(it) }
            .asLiveData()

    override fun errorClickListener() {
        // If you want to do something
    }
}