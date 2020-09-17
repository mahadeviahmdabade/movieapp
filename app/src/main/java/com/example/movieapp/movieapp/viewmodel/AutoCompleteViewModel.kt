package com.example.movieapp.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.movieapp.AutoCompleteRepository
import com.example.movieapp.movieapp.model.Content
import javax.inject.Inject

abstract class AutoCompleteViewModel : ViewModel() {
    @Inject
    lateinit var autoCompleteRepository: AutoCompleteRepository

    abstract fun getAutoCompleteLiveData(): LiveData<Content>

    abstract fun updateQueryString(query: String)
}