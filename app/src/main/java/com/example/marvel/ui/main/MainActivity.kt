package com.example.marvel.ui.main

import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.marvel.R
import com.example.marvel.api.models.characterList.Character
import com.example.marvel.api.models.characterList.CharacterListResponse
import com.example.marvel.ui.base.BaseComponentActivity
import com.example.marvel.ui.detail.DetailActivity
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseComponentActivity<MainViewModel>() {
    override val viewModel: MainViewModel by viewModels()

    @Composable
    override fun ProvideCompose() {
        LiveDataCharacterList(viewModel = viewModel, navigateToProfile = {
            startActivity(DetailActivity.newIntent(applicationContext,it))
        })
    }

    @Composable
    fun LiveDataCharacterList(viewModel: MainViewModel, navigateToProfile: (Character) -> Unit) {
        val character by viewModel.fetchCharacter.observeAsState(initial = null)
        val textState = remember { mutableStateOf(TextFieldValue("")) }
        Column {
            SearchView(textState)
            character?.let {
                CharacterList(it,textState,navigateToProfile)
            }
        }
    }

    @Composable
    fun CharacterList(characterListResponse: CharacterListResponse, state: MutableState<TextFieldValue>, navigateToProfile: (Character) -> Unit) {
        val characterList = remember { characterListResponse.data.results }
        var filteredCharacter: List<Character>
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
            val searchedText = state.value.text
            filteredCharacter = if (searchedText.isEmpty()) {
                characterList
            } else {
                val resultList = ArrayList<Character>()
                for (character in characterList) {
                    if (character.name.lowercase(Locale.getDefault())
                            .contains(searchedText.lowercase(Locale.getDefault()))
                    ) {
                        resultList.add(character)
                    }
                }
                resultList
            }
            items(
                items = filteredCharacter
            ) {
                CharacterListItem(it, navigateToProfile)
            }
        }
    }

    @Composable
    fun CharacterListItem(character: Character, navigateToProfile: (Character) -> Unit) {
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(),
            backgroundColor = colorResource(R.color.light_gray),
            elevation = 2.dp,
            shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        ) {
            Row(Modifier.clickable { navigateToProfile(character) }) {
                CharacterImage(character)
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = character.name)
                }
            }
        }
    }

    @Composable
    private fun CharacterImage(character: Character) {
        Image(
            painter = rememberImagePainter(
                data = "${character.thumbnail.path}/standard_medium.${character.thumbnail.extension}",
                builder = {
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .padding(2.dp)
        )
    }

    @Composable
    fun SearchView(state: MutableState<TextFieldValue>) {
        TextField(
            value = state.value,
            onValueChange = { value -> state.value = value },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier.padding(15.dp).size(24.dp)
                )
            },
            trailingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(
                        onClick = { state.value = TextFieldValue("") }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier.padding(15.dp).size(24.dp)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RectangleShape,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.primary,
                textColor = Color.White,
                cursorColor = Color.White,
                leadingIconColor = Color.White,
                trailingIconColor = Color.White
            )
        )
    }
}