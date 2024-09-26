package com.maxidev.newnews.feature.home.data.datasource.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.maxidev.newnews.core.utils.Constants.PAGE_SIZE
import com.maxidev.newnews.core_local.local.AppDataBase
import com.maxidev.newnews.core_local.local.entity.SearchNewsEntity
import com.maxidev.newnews.core_local.local.entity.entity_key.SearchNewsEntityKey
import com.maxidev.newnews.core_remote.remote.ApiService
import com.maxidev.newnews.feature.home.domain.mapper.asSearchEntity
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

private const val RELEVANT_CONTENT = "relevance"

@OptIn(ExperimentalPagingApi::class)
class SearchMediator(
    private val query: String,
    private val api: ApiService,
    private val dataBase: AppDataBase
): RemoteMediator<Int, SearchNewsEntity>() {

    private val searchDao = dataBase.searchNewsDao()
    private val searchRemoteKeyDao = dataBase.searchNewsDaoKey()

    override suspend fun initialize(): InitializeAction {
        val remoteKey = dataBase.withTransaction {
            searchRemoteKeyDao.remoteKeyByQuery(query)
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)

        return if((System.currentTimeMillis() - remoteKey.lastUpdated) >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchNewsEntity>
    ): MediatorResult {
        Log.d("GamesRemoteMediator", "Load type: $loadType")
        val loadKey = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.plus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevPage = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                prevPage
            }
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                val nextPage = remoteKey?.nextKey
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                nextPage
            }
        }
        Log.d("GamesRemoteMediator", "Load key: $loadKey")

        return try {

            val response = api.getNewsContent(
                query = query,
                orderBy = RELEVANT_CONTENT,
                pageSize = PAGE_SIZE,
                page = loadKey
            )
            val endOfPaginationReached = response.response?.results?.isEmpty() == true
            val prevPage = if (loadKey == 1) null else loadKey - 1
            val nextKey = if (endOfPaginationReached) null else loadKey + 1

            Log.d("GamesRemoteMediator", "End of pagination reached: $endOfPaginationReached")

            dataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    searchDao.clearAll()
                    searchRemoteKeyDao.deleteByQuery()
                }

                val key = response.response?.results?.map { result ->
                    SearchNewsEntityKey(
                        id = result?.id.orEmpty(),
                        nextKey = nextKey,
                        prevKey = prevPage,
                        lastUpdated = System.currentTimeMillis()
                    )
                }

                searchRemoteKeyDao.insertOrReplace(key.orEmpty())
                response.asSearchEntity()?.let { data -> searchDao.insertContentNews(data) }
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            Log.e("GamesRemoteMediator", "IOException", e)
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            Log.e("GamesRemoteMediator", "HttpException", e)
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, SearchNewsEntity>
    ): SearchNewsEntityKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                searchRemoteKeyDao.remoteKeyByQuery(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, SearchNewsEntity>
    ): SearchNewsEntityKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { data ->
                searchRemoteKeyDao.remoteKeyByQuery(data.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, SearchNewsEntity>
    ): SearchNewsEntityKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { data ->
                searchRemoteKeyDao.remoteKeyByQuery(data.id)
            }
    }
}