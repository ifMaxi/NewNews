package com.maxidev.newnews.feature.home.domain.mapper

import com.maxidev.newnews.core_local.local.entity.ContentNewsEntity
import com.maxidev.newnews.core_remote.remote.model_remote.ContentNewsRemote
import com.maxidev.newnews.feature.home.domain.model.ContentNewsDTO

fun ContentNewsRemote.asContentEntity() =
    this.response?.results?.map { data ->
        ContentNewsEntity(
            id = data?.id.orEmpty(),
            webTitle = data?.webTitle.orEmpty(),
            thumbnail = data?.fields?.thumbnail.orEmpty(),
            webUrl = data?.webUrl.orEmpty(),
            sectionName = data?.sectionName.orEmpty()
        )
    }

fun ContentNewsEntity.asDomain() =
    ContentNewsDTO(
        id = id,
        webTitle = webTitle,
        thumbnail = thumbnail,
        webUrl = webUrl,
        sectionName = sectionName
    )