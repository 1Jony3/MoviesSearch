package com.example.moviessearch.model.data.di

import com.example.moviessearch.model.data.api.IMoviesAPI
import org.junit.Before
import com.example.moviessearch.model.data.repository.MoviesRepository
import com.example.moviessearch.screen.ListMoviesViewModel
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Retrofit

class KoinModulesTest : KoinTest {

    @Before
    fun setUp() {

        startKoin {
            modules(listOf(networkModule, appModule))
        }
    }

    @Test
    fun testNetworkModule() {

        val retrofit: Retrofit by inject()
        val api: IMoviesAPI by inject()

        assertNotNull(retrofit)
        assertNotNull(api)
    }

    @Test
    fun testAppModule() {

        val repository: MoviesRepository by inject()
        val viewModel: ListMoviesViewModel by inject()

        assertNotNull(repository)
        assertNotNull(viewModel)
    }

    @After
    fun tearDown() {
        // Останавливаем Koin после тестов
        stopKoin()
    }
}