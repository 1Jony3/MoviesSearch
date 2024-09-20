package com.example.moviessearch.model.data.repository

import com.example.moviessearch.model.data.api.IMoviesAPI

class MoviesRepository (private val api: IMoviesAPI) {

    suspend fun getMovies() = api.getMovies()
}