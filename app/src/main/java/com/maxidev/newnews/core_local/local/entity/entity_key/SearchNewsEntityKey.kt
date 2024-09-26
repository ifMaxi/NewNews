package com.maxidev.newnews.core_local.local.entity.entity_key

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_news_table_key")
data class SearchNewsEntityKey(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val nextKey: Int?,
    val prevKey: Int?,
    val lastUpdated: Long
)