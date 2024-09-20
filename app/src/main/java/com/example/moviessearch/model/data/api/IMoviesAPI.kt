package com.example.moviessearch.model.data.api

import com.google.gson.JsonObject
import retrofit2.http.GET

interface IMoviesAPI {
    @GET("/sequeniatesttask/films.json")
    suspend fun getMovies(): JsonObject
}
