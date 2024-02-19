package com.example.newsrecap.di

import android.content.Context
import androidx.room.Room
import com.example.newsrecap.data.local.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNewsDatabase(
        @ApplicationContext appContext: Context
    ) = Room.databaseBuilder(
        context = appContext,
        klass = NewsDatabase::class.java,
        name = "news"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase) = newsDatabase.newsDao

}