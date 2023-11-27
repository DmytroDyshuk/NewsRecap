package com.example.newsrecap.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsrecap.data.database.model.DbNews

@Dao
interface NewsDao {
    @Query("SELECT * FROM DbNews")
    fun getNews(): LiveData<List<DbNews>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNews(news: List<DbNews>)
}