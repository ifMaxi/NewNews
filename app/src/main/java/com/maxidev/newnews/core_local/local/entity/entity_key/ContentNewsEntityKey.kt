package com.maxidev.newnews.core_local.local.entity.entity_key

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content_news_key_table")
data class ContentNewsEntityKey(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val nextKey: Int?,
    val prevKey: Int?,
    val lastUpdated: Long
)