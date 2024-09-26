package com.maxidev.newnews.core_remote.remote

import com.maxidev.newnews.core.utils.Constants.CONTENT
import com.maxidev.newnews.core_remote.remote.model_remote.ContentNewsRemote
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(CONTENT)
    suspend fun getNewsContent(
        @Query("q") query: String,
        @Query("order-by") orderBy: String,
        @Query("show-fields") showFields: String = "thumbnail",
        @Query("page-size") pageSize: Int,
        @Query("page") page: Int
    ): ContentNewsRemote
}