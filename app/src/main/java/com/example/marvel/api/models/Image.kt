package com.example.marvel.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(val path: String, val extension: String) : Parcelable
