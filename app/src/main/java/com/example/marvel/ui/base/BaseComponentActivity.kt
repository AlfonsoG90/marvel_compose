package com.example.marvel.ui.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.marvel.R
import com.example.marvel.ui.base.theme.MarvelTheme

abstract class BaseComponentActivity<vm : ViewModelBase> : ComponentActivity() {
    abstract val viewModel: vm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ProvideCompose()
                    SetUpLoadingDialog()
                    SetUpErrorDialog()
                }
            }
        }
    }

    @Composable
    abstract fun ProvideCompose()

    @Composable
    private fun SetUpLoadingDialog() {
        val loadingValue = viewModel.loadingVisibility.observeAsState()
        if (loadingValue.value == true) {
            ShowLoading()
        }
    }

    @Composable
    private fun SetUpErrorDialog() {
        val error = viewModel.errorMessage.observeAsState()
        error.value?.let {
            if (it != 0) {
                ShowErrorDialog(it)
            }
        }
    }

    @Composable
    fun ShowLoading() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally))
        }
    }

    @Composable
    private fun ShowErrorDialog(errorMessage: Int) {
        val scaffoldState = rememberScaffoldState()
        val message = stringResource(id = errorMessage)
        val acctionLabel = stringResource(id = R.string.accept)
        Scaffold(
            modifier = Modifier,
            scaffoldState = scaffoldState
        ) {
            LaunchedEffect(Unit) {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = acctionLabel,
                    duration = SnackbarDuration.Indefinite
                )
                if (snackbarResult == SnackbarResult.ActionPerformed) {
                    viewModel.errorClickListener()
                }
            }
        }
    }
}