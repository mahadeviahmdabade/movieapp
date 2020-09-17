package com.example.movieapp.movieapp.util

import android.content.Context
import androidx.room.Room
import com.example.movieapp.movieapp.db.AppDatabase

object DatabaseAccessor {

    private lateinit var db : AppDatabase
    private lateinit var context :Context
    fun getDb()  : AppDatabase {
        if (!DatabaseAccessor::db.isInitialized) {
            initializedb(context)
        }
        return db
    }

    fun initializedb(applicationContext : Context) {
        context = applicationContext

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

    }
}