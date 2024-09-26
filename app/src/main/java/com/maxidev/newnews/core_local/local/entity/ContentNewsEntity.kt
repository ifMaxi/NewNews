package com.maxidev.newnews.core_local.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content_news_table")
data class ContentNewsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val webTitle: String,
    val thumbnail: String,
    val webUrl: String,
    val sectionName: String
)