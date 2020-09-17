package com.example.movieapp.movieapp

import androidx.lifecycle.LiveData
import com.example.movieapp.movieapp.model.Content

interface AutoCompleteRepository {

    fun getAutoCompleteLiveData() : LiveData<Content>

    fun updateQueryString(query : String)
}