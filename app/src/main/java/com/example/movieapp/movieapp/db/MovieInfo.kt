package com.example.movieapp.movieapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieInfo(val Title : String, val Year : String, @PrimaryKey val imdbID : String,val Poster : String?) {

    override fun equals(other : Any?): Boolean {
        if (other !=null && other is String) {
            if (other.equals(imdbID)) {
                return true
            }
            return false
        }
        return false
    }
}
