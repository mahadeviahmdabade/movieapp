package com.example.movieapp.movieapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieInfo : MovieInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listOfMovieInfo : List<MovieInfo>)

    @Query("Select * From MovieInfo where Title like '%' || :query  || '%'")
    fun getMoviesInfo(query : String) : List<MovieInfo>?
}