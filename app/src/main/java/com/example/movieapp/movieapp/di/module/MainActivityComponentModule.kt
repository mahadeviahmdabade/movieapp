package com.example.movieapp.movieapp.di.module

import com.example.movieapp.movieapp.db.AppDatabase
import com.example.movieapp.movieapp.view.MainActivityRepository
import com.example.movieapp.movieapp.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainActivityComponentModule {

    @Provides
    fun getRepository(retrofit: Retrofit?, appDatabase: AppDatabase) : MainActivityRepository {
        return RepositoryImpl(
            retrofit,
            appDatabase
        )
    }

}