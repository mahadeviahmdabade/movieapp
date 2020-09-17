package com.example.movieapp.movieapp.repository

import com.example.movieapp.movieapp.model.Content

interface Repository {

    fun getAutoCompleteList(query : String) : Content

    fun getMoviesList() : Content
}