package com.maxidev.newnews

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers.IO

@HiltAndroidApp
class AppApplication: Application(), ImageLoaderFactory {

    private val cachePolicy = CachePolicy.ENABLED

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCachePolicy(cachePolicy)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.04)
                    .build()
            }
            .diskCachePolicy(cachePolicy)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.02)
                    .directory(cacheDir.resolve("image_cache"))
                    .build()
            }
            .crossfade(true)
            .allowRgb565(true)
            .decoderDispatcher(IO)
            .allowHardware(true)
            .respectCacheHeaders(false)
            .logger(DebugLogger())
            .build()
    }
}