package com.maxidev.newnews.core_remote.remote.model_remote

import kotlinx.serialization.Serializable

@Serializable
data class ContentNewsRemote(
    val response: Response? = Response()
)