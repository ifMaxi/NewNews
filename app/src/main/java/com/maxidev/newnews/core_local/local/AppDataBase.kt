package com.maxidev.newnews.core_local.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maxidev.newnews.core_local.local.dao.ContentNewsDao
import com.maxidev.newnews.core_local.local.dao.SearchNewsDao
import com.maxidev.newnews.core_local.local.dao.dao_key.ContentNewsDaoKey
import com.maxidev.newnews.core_local.local.dao.dao_key.SearchNewsDaoKey
import com.maxidev.newnews.core_local.local.entity.ContentNewsEntity
import com.maxidev.newnews.core_local.local.entity.SearchNewsEntity
import com.maxidev.newnews.core_local.local.entity.entity_key.ContentNewsEntityKey
import com.maxidev.newnews.core_local.local.entity.entity_key.SearchNewsEntityKey

@Database(
    entities = [
        ContentNewsEntity::class,
        ContentNewsEntityKey::class,
        SearchNewsEntity::class,
        SearchNewsEntityKey::class
               ],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase: RoomDatabase() {

    abstract fun contentNewsDao(): ContentNewsDao
    abstract fun contentNewsDaoKey(): ContentNewsDaoKey

    abstract fun searchNewsDao(): SearchNewsDao
    abstract fun searchNewsDaoKey(): SearchNewsDaoKey
}