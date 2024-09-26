package com.maxidev.newnews.feature.home.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.maxidev.newnews.core.presentation.theme.NewNewsTheme
import com.maxidev.newnews.core.presentation.theme.latoFamily
import com.maxidev.newnews.core.presentation.theme.poppinsFamily
import com.maxidev.newnews.feature.home.domain.model.ContentNewsDTO
import com.maxidev.newnews.feature.home.domain.model.SearchNewsDTO
import com.maxidev.newnews.feature.home.ui.components.SearchBarItem

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    contentListItem: LazyPagingItems<ContentNewsDTO>,
    searchListItem: LazyPagingItems<SearchNewsDTO>,
    lazyState: LazyListState,
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onClearText: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.statusBars,
        topBar = {
            Text(
                text = "New News",
                fontSize = 30.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 26.dp)
                    .statusBarsPadding()
            )
            SearchBarItem(
                value = value,
                onValueChange = onValueChange,
                onSearch = onSearch,
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                onClearText = onClearText,
                modifier = Modifier
                    .padding(WindowInsets.statusBars.asPaddingValues())
            )
        }
    ) { innerPadding ->
        when {
            value.isBlank() || value.isEmpty() -> {
                ContentView(
                    contentListItem = contentListItem,
                    lazyState = lazyState,
                    modifier = Modifier
                        .padding(innerPadding)
                )
            }
            else -> {
                SearchView(
                    searchListItem = searchListItem,
                    lazyState = lazyState,
                    modifier = Modifier
                        .padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun ContentView(
    modifier: Modifier = Modifier,
    contentListItem: LazyPagingItems<ContentNewsDTO>,
    lazyState: LazyListState
) {
    val contentState = remember(contentListItem) { contentListItem }

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = lazyState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(14.dp)
    ) {
        item {
            Text(
                text = "Last News",
                fontSize = 26.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
        }
        items(
            count = contentState.itemCount,
            key = contentState.itemKey { key -> key.id },
            contentType = contentState.itemContentType { contentType -> contentType.id }
        ) { data ->
            contentState[data]?.let { item ->
                HorizontalNewsItem(
                    title = item.webTitle,
                    thumbnail = item.thumbnail,
                    section = item.sectionName,
                    url = item.webUrl
                )
            }
            HorizontalDivider()
        }
    }
}

@Composable
private fun SearchView(
    modifier: Modifier = Modifier,
    searchListItem: LazyPagingItems<SearchNewsDTO>,
    lazyState: LazyListState
) {
    val searchState = remember(searchListItem) { searchListItem }

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = lazyState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(14.dp)
    ) {
        items(
            count = searchState.itemCount,
            key = searchState.itemKey { key -> key.id },
            contentType = searchState.itemContentType { contentType -> contentType.id }
        ) { data ->
            searchState[data]?.let { item ->
                HorizontalNewsItem(
                    title = item.webTitle,
                    thumbnail = item.thumbnail,
                    section = item.sectionName,
                    url = item.webUrl
                )
            }
            HorizontalDivider()
        }
    }
}

@Composable
private fun HorizontalNewsItem(
    modifier: Modifier = Modifier,
    title: String,
    thumbnail: String,
    section: String,
    url: String
) {
    val context = LocalContext.current
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { startActivity(context, intent, null) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontFamily = latoFamily,
                textAlign = TextAlign.Start,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(240.dp)
            )
            Text(
                text = section,
                fontSize = 10.sp,
                fontFamily = latoFamily,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .align(Alignment.Start)
            )
        }
        AsyncImage(
            model = thumbnail,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(5))
                .size(90.dp)
        )
    }
}

@Preview
@Composable
private fun HorizontalNewsPreview() {
    NewNewsTheme {
        HorizontalNewsItem(
            title = "Lorem Title, Lorem Title, Lorem Title, Lorem Title, Lorem Title, Lorem Title",
            thumbnail = "Lorem Image",
            section = "Lorem Section",
            url = "Url"
        )
    }
}