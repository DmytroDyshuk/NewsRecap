package com.example.newsrecap.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsrecap.database.model.DatabaseNews

@Dao
interface NewsDao {
    @Query("SELECT * FROM DatabaseNews")
    fun getNews(): LiveData<List<DatabaseNews>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNews(news: List<DatabaseNews>)
}