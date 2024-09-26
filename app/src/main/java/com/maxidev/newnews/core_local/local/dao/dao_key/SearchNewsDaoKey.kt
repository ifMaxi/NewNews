package com.maxidev.newnews.core_local.local.dao.dao_key

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.maxidev.newnews.core_local.local.entity.entity_key.SearchNewsEntityKey

@Dao
interface SearchNewsDaoKey {

    @Upsert
    suspend fun insertOrReplace(remoteKey: List<SearchNewsEntityKey>)

    @Query("SELECT * FROM search_news_table_key WHERE id = :query")
    suspend fun remoteKeyByQuery(query: String): SearchNewsEntityKey

    @Query("DELETE FROM search_news_table_key")
    suspend fun deleteByQuery()
}