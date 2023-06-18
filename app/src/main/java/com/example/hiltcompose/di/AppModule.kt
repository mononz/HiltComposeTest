package com.example.hiltcompose.di

import android.app.Application
import android.content.Context
import com.example.hiltcompose.domain.Storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesStorage(application: Application): Storage {
        val prefs = application.getSharedPreferences("AppStorage", Context.MODE_PRIVATE)
        return Storage(prefs)
    }
}