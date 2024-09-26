package com.maxidev.newnews.feature.home.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.maxidev.newnews.core.utils.Constants.PAGE_SIZE
import com.maxidev.newnews.core_local.local.AppDataBase
import com.maxidev.newnews.core_remote.remote.ApiService
import com.maxidev.newnews.feature.home.data.datasource.paging.ContentMediator
import com.maxidev.newnews.feature.home.data.datasource.paging.SearchMediator
import com.maxidev.newnews.feature.home.domain.mapper.asDomain
import com.maxidev.newnews.feature.home.domain.model.ContentNewsDTO
import com.maxidev.newnews.feature.home.domain.model.SearchNewsDTO
import com.maxidev.newnews.feature.home.domain.repository.ContentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ContentRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dataBase: AppDataBase
): ContentRepository {

    override fun fetchContentNews(): Flow<PagingData<ContentNewsDTO>> {

        val pagingFactory = {
            dataBase.contentNewsDao().getAllContentNews()
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 2,
                enablePlaceholders = true,
                initialLoadSize = 6
            ),
            remoteMediator = ContentMediator(
                api = api,
                dataBase = dataBase
            ),
            pagingSourceFactory = pagingFactory
        ).flow
            .map { pagingData ->
                pagingData.map { it.asDomain() }
            }
    }

    override fun fetchSearchNews(query: String): Flow<PagingData<SearchNewsDTO>> {

        val pagingFactory = {
            dataBase.searchNewsDao().getAllContentNews(query)
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 2,
                enablePlaceholders = true,
                initialLoadSize = 6
            ),
            remoteMediator = SearchMediator(
                query = query,
                api = api,
                dataBase = dataBase
            ),
            pagingSourceFactory = pagingFactory
        ).flow
            .map { pagingData ->
                pagingData.map { it.asDomain() }
            }
    }
}