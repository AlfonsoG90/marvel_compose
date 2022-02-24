package com.example.marvel.utils.extension

import com.example.marvel.ui.base.ViewModelBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.useLoading(vm: ViewModelBase): Flow<T> = this
    .onStart { vm.onRetrievePostListStart() }
    .onCompletion { vm.onRetrievePostListFinish() }
