package com.example.marvel.api.models.characterList

import android.os.Parcelable
import com.example.marvel.api.models.Image
import com.example.marvel.api.models.ResourceList
import com.example.marvel.api.models.Url
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(val id: Int, val name: String, val description: String, val thumbnail: Image, val urls: List<Url>, val comics: ResourceList, val stories: ResourceList, val events: ResourceList,val series: ResourceList,) : Parcelable
