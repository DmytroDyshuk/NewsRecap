package com.example.newsrecap.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsrecap.database.converter.SourceConverter
import com.example.newsrecap.database.dao.NewsDao
import com.example.newsrecap.database.model.DatabaseNews

@Database(entities = [DatabaseNews::class], version = 5)
@TypeConverters(SourceConverter::class)
abstract class NewsDatabase: RoomDatabase() {
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