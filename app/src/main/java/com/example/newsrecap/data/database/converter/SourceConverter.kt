package com.example.newsrecap.data.database.converter

import androidx.room.TypeConverter
import com.example.newsrecap.domain.model.Source
import com.google.gson.Gson

class SourceConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromSource(source: Source): String {
        return gson.toJson(source)
    }

    @TypeConverter
    fun toSource(sourceJson: String): Source {
        return gson.fromJson(sourceJson, Source::class.java)
    }
}