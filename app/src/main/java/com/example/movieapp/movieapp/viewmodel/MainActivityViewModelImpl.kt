package com.example.movieapp.movieapp.viewmodel

import androidx.lifecycle.LiveData
import com.example.movieapp.movieapp.model.Content
import com.example.movieapp.movieapp.di.component.DaggerMainActivityComponent

class MainActivityViewModelImpl : MainActivityViewModel() {

    init {
        DaggerMainActivityComponent.builder().build().inject(this)
    }

    override fun getMoviesListLiveData(): LiveData<Content> {
        return mainActivityRepository.getMoviesListLiveData()
    }

    override fun searchMovies(title: String, pageNo: Int) {
        mainActivityRepository.searchMovies(title,pageNo)
    }
}