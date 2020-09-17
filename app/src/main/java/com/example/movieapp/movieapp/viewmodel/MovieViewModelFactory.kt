package com.example.movieapp.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieViewModelFactory(): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.simpleName.equals("MainActivityViewModel")) {
            return MainActivityViewModelImpl() as T
        }
        else if (modelClass.simpleName.equals("AutoCompleteViewModel")) {
            return AutoCompleteActivityViewModelImpl() as T
        }
        return modelClass.newInstance()
    }
}