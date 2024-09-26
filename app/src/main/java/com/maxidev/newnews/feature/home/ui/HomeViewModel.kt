package com.maxidev.newnews.feature.home.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.maxidev.newnews.feature.home.domain.model.ContentNewsDTO
import com.maxidev.newnews.feature.home.domain.model.SearchNewsDTO
import com.maxidev.newnews.feature.home.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ContentRepository
): ViewModel() {

    private val _contentPagerState = MutableStateFlow<PagingData<ContentNewsDTO>>(PagingData.empty())
    val contentPagerState = _contentPagerState

    private val _searchPagerState = MutableStateFlow<PagingData<SearchNewsDTO>>(PagingData.empty())
    val searchPagerState = _searchPagerState

    private val _query: MutableState<String> = mutableStateOf("")
    val query: State<String> = _query

    init {
        contentNews()
    }

    fun onQueryChange(query: String) {
        _query.value = query
    }

    fun clearText() {
        _query.value = ""
    }

    fun searchedNews(query: String) = viewModelScope.launch {
        repository.fetchSearchNews(query)
            .cachedIn(viewModelScope)
            .collect { _searchPagerState.value = it }
    }

    private fun contentNews() = viewModelScope.launch {
        repository.fetchContentNews()
            .cachedIn(viewModelScope)
            .collect { _contentPagerState.value = it }
    }
}