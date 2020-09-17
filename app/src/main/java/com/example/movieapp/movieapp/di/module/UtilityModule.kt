package com.example.movieapp.movieapp.di.module

import com.example.movieapp.movieapp.db.AppDatabase
import com.example.movieapp.movieapp.util.DatabaseAccessor
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class UtilityModule() {

    @Singleton
    @Provides
    fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getDb() : AppDatabase {
        return DatabaseAccessor.getDb()
    }

}