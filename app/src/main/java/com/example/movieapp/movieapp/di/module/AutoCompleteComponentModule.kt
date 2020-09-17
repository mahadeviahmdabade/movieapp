package com.example.movieapp.movieapp.di.module

import com.example.movieapp.movieapp.db.AppDatabase
import com.example.movieapp.movieapp.AutoCompleteRepository
import com.example.movieapp.movieapp.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AutoCompleteComponentModule {

    @Provides
    fun getRepository(retrofit: Retrofit?,appDatabase: AppDatabase?) : AutoCompleteRepository {
        return RepositoryImpl(
            retrofit,
            appDatabase
        )
    }


}