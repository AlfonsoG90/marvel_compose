package com.example.marvel.ui.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.marvel.R
import com.example.marvel.api.models.ResourceSummary
import com.example.marvel.api.models.Url
import com.example.marvel.api.models.characterList.Character
import com.example.marvel.ui.base.theme.MarvelTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val character = intent.getParcelableExtra<Character>(CHARACTER)
                    character?.let {
                        ShowDetails(character = it)
                    }
                }
            }
        }
    }

    @Composable
    fun ShowDetails(character: Character) {
        val scrollState = rememberScrollState()

        Column {
            BoxWithConstraints {
                Surface {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState),
                    ) {
                        ProfileHeader(
                            character,
                            this@BoxWithConstraints.maxHeight
                        )
                        ProfileContent(character)
                    }
                }
            }
        }
    }

    @Composable
    private fun ProfileHeader(
        character: Character,
        containerHeight: Dp
    ) {
        Image(
            modifier = Modifier
                .heightIn(max = containerHeight / 2)
                .fillMaxWidth(),
            painter = rememberImagePainter(
                data = "${character.thumbnail.path}/landscape_xlarge.${character.thumbnail.extension}"
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }

    @Composable
    private fun ProfileContent(character: Character) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Name(character)
            Description(character)
            if (character.comics.items.isNotEmpty()) {
                NameResource(stringResource(R.string.comics))
                ResourceList(character.comics.items)
            }
            if (character.series.items.isNotEmpty()) {
                NameResource(stringResource(R.string.series))
                ResourceList(character.series.items)
            }
            if (character.stories.items.isNotEmpty()) {
                NameResource(stringResource(R.string.stories))
                ResourceList(character.stories.items)
            }
            if (character.events.items.isNotEmpty()) {
                NameResource(stringResource(R.string.events))
                ResourceList(character.events.items)
            }
            if (character.urls.isNotEmpty()) {
                NameResource(stringResource(R.string.more_info))
                LinkList(character.urls)
            }
            Spacer(Modifier.height((20.dp)))
        }
    }

    @Composable
    private fun Name(character: Character) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold
            )
        }
    }

    @Composable
    private fun Description(character: Character) {
        if (character.description.isNotEmpty()) {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                Text(
                    text = character.description,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }

    @Composable
    private fun NameResource(name: String) {
        Column(modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 5.dp)) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold
            )
        }
    }

    @Composable
    private fun ResourceList(list: List<ResourceSummary>) {
        LazyRow {
            items(
                items = list
            ) {
                SummaryItem(it)
            }
        }
    }

    @Composable
    fun SummaryItem(summary: ResourceSummary) {
        Card(
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 2.dp)
                .fillMaxSize(),
            elevation = 2.dp,
            shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        ) {
            Row {
                Text(
                    text = summary.name,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .padding(10.dp)
                )
            }
        }
    }

    @Composable
    private fun LinkList(list: List<Url>) {
        Column {
            list.forEach {
                LinkItem(it)
            }
        }
    }

    @Composable
    fun LinkItem(url: Url) {
        Row(
            Modifier
                .padding(vertical = 5.dp)
                .clickable {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url.url)))
                }) {
            Text(
                text = url.type.replaceFirstChar { it.uppercase() },
                color = Color.Companion.Blue,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(start = 16.dp)
            )
        }
    }


    companion object {
        private const val CHARACTER = "character"
        fun newIntent(packageContext: Context?, character: Character): Intent {
            return Intent(packageContext, DetailActivity::class.java).apply {
                putExtra(CHARACTER, character)
            }
        }
    }
}