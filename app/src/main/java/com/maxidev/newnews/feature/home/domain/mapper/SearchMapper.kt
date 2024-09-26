package com.maxidev.newnews.feature.home.domain.mapper

import com.maxidev.newnews.core_local.local.entity.SearchNewsEntity
import com.maxidev.newnews.core_remote.remote.model_remote.ContentNewsRemote
import com.maxidev.newnews.feature.home.domain.model.SearchNewsDTO

fun ContentNewsRemote.asSearchEntity() =
    this.response?.results?.map { data ->
        SearchNewsEntity(
            id = data?.id.orEmpty(),
            webTitle = data?.webTitle.orEmpty(),
            thumbnail = data?.fields?.thumbnail.orEmpty(),
            webUrl = data?.webUrl.orEmpty(),
            sectionName = data?.sectionName.orEmpty()
        )
    }

fun SearchNewsEntity.asDomain() =
    SearchNewsDTO(
        id = id,
        webTitle = webTitle,
        thumbnail = thumbnail,
        webUrl = webUrl,
        sectionName = sectionName
    )