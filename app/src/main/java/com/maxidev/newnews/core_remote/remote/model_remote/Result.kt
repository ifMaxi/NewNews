package com.maxidev.newnews.core_remote.remote.model_remote

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val id: String? = "",
    val type: String? = "",
    val sectionId: String? = "",
    val sectionName: String? = "",
    val webPublicationDate: String? = "",
    val webTitle: String? = "",
    val webUrl: String? = "",
    val apiUrl: String? = "",
    val fields: Fields? = Fields(),
    val isHosted: Boolean? = false,
    val pillarId: String? = "",
    val pillarName: String? = ""
)