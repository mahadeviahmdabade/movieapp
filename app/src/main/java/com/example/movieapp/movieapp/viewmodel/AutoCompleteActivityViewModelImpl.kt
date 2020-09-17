package com.example.movieapp.movieapp.viewmodel

import androidx.lifecycle.LiveData
import com.example.movieapp.movieapp.model.Content
import com.example.movieapp.movieapp.di.component.DaggerAutoCompleteComponent

class AutoCompleteActivityViewModelImpl : AutoCompleteViewModel() {


    init {
        DaggerAutoCompleteComponent.builder().build().inject(this)
    }
    override fun getAutoCompleteLiveData(): LiveData<Content> {
        return autoCompleteRepository.getAutoCompleteLiveData()

    }

    override fun updateQueryString(query: String) {
        autoCompleteRepository.updateQueryString(query)
    }
}