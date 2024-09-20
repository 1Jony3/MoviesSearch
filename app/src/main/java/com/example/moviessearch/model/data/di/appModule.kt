package com.example.moviessearch.model.data.di

import com.example.moviessearch.model.data.repository.MoviesRepository
import com.example.moviessearch.screen.ListMoviesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { MoviesRepository(get()) }
    viewModel { ListMoviesViewModel(get()) }
}