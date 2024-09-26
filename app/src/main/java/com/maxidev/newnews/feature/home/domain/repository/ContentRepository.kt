package com.maxidev.newnews.feature.home.domain.repository

import androidx.paging.PagingData
import com.maxidev.newnews.feature.home.domain.model.ContentNewsDTO
import com.maxidev.newnews.feature.home.domain.model.SearchNewsDTO
import kotlinx.coroutines.flow.Flow

interface ContentRepository {

    fun fetchContentNews(): Flow<PagingData<ContentNewsDTO>>

    fun fetchSearchNews(query: String): Flow<PagingData<SearchNewsDTO>>
}