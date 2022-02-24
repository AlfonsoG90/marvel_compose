package com.example.marvel.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResourceList(val returned: Int, val items: List<ResourceSummary>): Parcelable
