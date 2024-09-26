package com.maxidev.newnews.core_remote.remote

import com.maxidev.newnews.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {

    // Replace "API_KEY" with your actual API key.
    private val apiKey = BuildConfig.api_key

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url
            .newBuilder()
            .addQueryParameter("api-key", apiKey)
            .build()

        request = request
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}