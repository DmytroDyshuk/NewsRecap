package com.example.newsrecap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsrecap.data.local.converter.SourceConverter
import com.example.newsrecap.data.local.dao.NewsDao
import com.example.newsrecap.data.local.entities.NewsEntity

@Database(entities = [NewsEntity::class], version = 14)
@TypeConverters(SourceConverter::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
}