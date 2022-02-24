package com.example.marvel.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvel.R
import retrofit2.HttpException
import java.net.UnknownHostException

abstract class ViewModelBase: ViewModel() {
    val errorMessage: MutableLiveData<Int> = MutableLiveData<Int>().apply {
        this.value = 0
    }
    val loadingVisibility = MutableLiveData<Boolean>().apply {
        this.value = false
    }

    abstract fun errorClickListener()

    fun onRetrievePostListStart() {
        loadingVisibility.value = true
        errorMessage.value = 0
    }

    fun onRetrievePostListFinish() {
        loadingVisibility.value = false
    }

    fun onRetrievePostListError(error: Throwable?) {
        when (error) {
            is UnknownHostException -> {
                errorMessage.value = R.string.error_noConection
            }
            is HttpException -> {
                errorMessage.value = R.string.error_conection
            }
            else -> {
                errorMessage.value = R.string.error_conection
            }
        }
    }
}