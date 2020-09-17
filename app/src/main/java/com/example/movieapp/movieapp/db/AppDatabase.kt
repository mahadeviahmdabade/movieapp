package com.example.movieapp.movieapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(MovieInfo::class), version = 1)
abstract class  AppDatabase : RoomDatabase(){

    public abstract fun getMovieInfoDao() : MovieInfoDao
}