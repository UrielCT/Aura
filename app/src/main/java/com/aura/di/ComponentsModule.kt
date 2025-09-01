package com.aura.di

import android.app.Application
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import org.koin.dsl.module

private fun provideImageLoader(context: Application) : ImageLoader {
    return ImageLoader.Builder(context)
        .memoryCache{
            MemoryCache.Builder(context)
                .maxSizePercent(0.25)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.25)
                .build()
        }
        .build()
}

val componentsModule = module {
    single { provideImageLoader( get() ) }
}