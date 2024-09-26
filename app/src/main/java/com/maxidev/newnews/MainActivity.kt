package com.maxidev.newnews

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.maxidev.newnews.core.presentation.theme.NewNewsTheme
import com.maxidev.newnews.feature.home.ui.HomeView
import com.maxidev.newnews.feature.home.ui.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }

            NewNewsTheme {
                val viewModel: HomeViewModel = hiltViewModel()
                val contentPagedItems = viewModel.contentPagerState.collectAsLazyPagingItems()
                val searchPagedItems = viewModel.searchPagerState.collectAsLazyPagingItems()
                val query by viewModel.query
                val focusManager = LocalFocusManager.current
                var expanded by remember { mutableStateOf(false) }
                val lazyListState = rememberLazyListState()

                HomeView(
                    contentListItem = contentPagedItems,
                    searchListItem = searchPagedItems,
                    lazyState = lazyListState,
                    value = query,
                    onValueChange = viewModel::onQueryChange,
                    onSearch = {
                        viewModel.searchedNews(it)
                        focusManager.clearFocus()
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = false },
                    onClearText = {
                        viewModel.clearText()
                        focusManager.clearFocus()
                    },
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = { focusManager.clearFocus() })
                        }
                )
            }
        }
    }
}