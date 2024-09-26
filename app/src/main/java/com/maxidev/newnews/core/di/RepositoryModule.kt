package com.maxidev.newnews.core.di

import com.maxidev.newnews.feature.home.data.datasource.ContentRepositoryImpl
import com.maxidev.newnews.feature.home.domain.repository.ContentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindContentRepository(impl: ContentRepositoryImpl): ContentRepository
}