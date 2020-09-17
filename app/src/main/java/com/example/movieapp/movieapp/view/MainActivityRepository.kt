package com.example.movieapp.movieapp.view

import androidx.lifecycle.LiveData
import com.example.movieapp.movieapp.model.Content

interface MainActivityRepository {

    fun getMoviesListLiveData() : LiveData<Content>

    fun searchMovies(title:String,pageNo:Int)

}