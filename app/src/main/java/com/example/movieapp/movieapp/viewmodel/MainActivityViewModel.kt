package com.example.movieapp.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.movieapp.model.Content
import com.example.movieapp.movieapp.view.MainActivityRepository
import javax.inject.Inject

abstract class MainActivityViewModel : ViewModel() {

    @Inject
    lateinit var mainActivityRepository: MainActivityRepository

    abstract fun getMoviesListLiveData(): LiveData<Content>

    abstract fun searchMovies(title: String, pageNo: Int)
}