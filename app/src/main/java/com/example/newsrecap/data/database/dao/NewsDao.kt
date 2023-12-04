package com.example.newsrecap.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsrecap.data.database.model.DbNews
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM DbNews")
    fun getNews(): Flow<List<DbNews>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNews(news: List<DbNews>)
}