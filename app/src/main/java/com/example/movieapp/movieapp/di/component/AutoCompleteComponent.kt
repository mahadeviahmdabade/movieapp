package com.example.movieapp.movieapp.di.component

import com.example.movieapp.movieapp.di.module.AutoCompleteComponentModule
import com.example.movieapp.movieapp.di.module.UtilityModule
import com.example.movieapp.movieapp.viewmodel.AutoCompleteViewModel
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    AutoCompleteComponentModule::class,
    UtilityModule::class))
interface AutoCompleteComponent {

    fun inject(autoCompleteActivityViewModel : AutoCompleteViewModel)

    fun getRetrofit() : Retrofit
}