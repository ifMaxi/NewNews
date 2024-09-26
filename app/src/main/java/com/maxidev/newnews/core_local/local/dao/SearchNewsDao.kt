package com.maxidev.newnews.core_local.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.maxidev.newnews.core_local.local.entity.SearchNewsEntity

@Dao
interface SearchNewsDao {

    @Query("SELECT * FROM search_news_table WHERE webTitle LIKE '%' || :query || '%'")
    fun getAllContentNews(query: String): PagingSource<Int, SearchNewsEntity>

    @Upsert
    suspend fun insertContentNews(contentNews: List<SearchNewsEntity>)

    @Query("DELETE FROM search_news_table")
    suspend fun clearAll()
}