package com.example.movieapp.movieapp.di.component

import com.example.movieapp.movieapp.di.module.MainActivityComponentModule
import com.example.movieapp.movieapp.di.module.UtilityModule
import com.example.movieapp.movieapp.viewmodel.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    MainActivityComponentModule::class,
    UtilityModule::class))
interface MainActivityComponent {

    fun inject(mainActivityViewModel: MainActivityViewModel)

}