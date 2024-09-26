package com.maxidev.newnews.core_local.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_news_table")
data class SearchNewsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val webTitle: String,
    val thumbnail: String,
    val webUrl: String,
    val sectionName: String
)
