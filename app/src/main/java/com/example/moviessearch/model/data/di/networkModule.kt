package com.example.moviessearch.model.data.di

import com.example.moviessearch.model.data.api.IMoviesAPI
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://s3-eu-west-1.amazonaws.com"
val networkModule = module {

    single { Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build() }

    single { get<Retrofit>().create(IMoviesAPI::class.java) }
}