package com.example.newsrecap.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsrecap.data.database.converter.SourceConverter
import com.example.newsrecap.data.database.dao.NewsDao
import com.example.newsrecap.data.database.model.DbNews

@Database(entities = [DbNews::class], version = 6)
@TypeConverters(SourceConverter::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
}

private lateinit var INSTANCE: NewsDatabase

fun getDatabase(context: Context): NewsDatabase {
    synchronized(NewsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, NewsDatabase::class.java, "news")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}