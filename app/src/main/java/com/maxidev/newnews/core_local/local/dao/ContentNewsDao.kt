package com.maxidev.newnews.core_local.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.maxidev.newnews.core_local.local.entity.ContentNewsEntity

@Dao
interface ContentNewsDao {

    @Query("SELECT * FROM content_news_table")
    fun getAllContentNews(): PagingSource<Int, ContentNewsEntity>

    @Upsert
    suspend fun insertContentNews(contentNews: List<ContentNewsEntity>)

    @Query("DELETE FROM content_news_table")
    suspend fun clearAll()
}