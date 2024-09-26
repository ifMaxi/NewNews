package com.maxidev.newnews.core_local.local.dao.dao_key

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.maxidev.newnews.core_local.local.entity.entity_key.ContentNewsEntityKey

@Dao
interface ContentNewsDaoKey {

    @Upsert
    suspend fun insertOrReplace(remoteKey: List<ContentNewsEntityKey>)

    @Query("SELECT * FROM content_news_key_table WHERE id = :query")
    suspend fun remoteKeyByQuery(query: String): ContentNewsEntityKey

    @Query("DELETE FROM content_news_key_table")
    suspend fun deleteByQuery()
}