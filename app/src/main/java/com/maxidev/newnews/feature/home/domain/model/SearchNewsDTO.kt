package com.maxidev.newnews.feature.home.domain.model

data class SearchNewsDTO(
    val id: String,
    val webTitle: String,
    val thumbnail: String,
    val webUrl: String,
    val sectionName: String
)