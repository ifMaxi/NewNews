package com.maxidev.newnews.core.di

import android.content.Context
import androidx.room.Room
import com.maxidev.newnews.core_local.local.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATA_BASE_NAME = "app_data_base"

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun providesDataBase(
        @ApplicationContext context: Context
    ): AppDataBase {

        return Room.databaseBuilder(
            context = context,
            klass = AppDataBase::class.java,
            name = DATA_BASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}