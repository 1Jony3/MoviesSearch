package com.example.moviessearch.screen

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviessearch.model.data.entities.Movie
import com.example.moviessearch.model.data.repository.MoviesRepository
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListMoviesViewModel(private val repository: MoviesRepository): ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    sealed interface State {
        data object Loading : State
        data class Loaded(val moviesList: List<Movie>) : State
        data class Error(val error: String) : State
    }

    var movies: List<Movie> = emptyList()
    var genres: List<String> = emptyList()
    val selectedGenre = MutableLiveData<String?>(null)

    init {
        getMovies()
    }

    fun getMovies() {
        _state.postValue(State.Loading)
        viewModelScope.launch {
            try {
                val moviesList = repository.getMovies()
                val list = convertFilms(moviesList.get("films") as JsonArray)
                creatingListOfGenres(list)
                movies = list
                _state.postValue(State.Loaded(list))
            } catch (e: Exception) {
                _state.postValue(State.Error("Error ${e.message}"))
            }
        }
    }

    private fun convertFilms(films: JsonArray): List<Movie> {
        return Gson().fromJson(films.toString(), object : TypeToken<List<Movie?>?>() {}.type)
    }

    private fun creatingListOfGenres(movies: List<Movie>) {
        val genres: MutableList<String> = mutableListOf()
        for (movie in movies) {
            movie.genres.let { genres.addAll(it) }
        }
        this.genres = genres.distinct()
    }

    fun toggleGenreFilter(genre: String) {
        when (val currentState = _state.value) {
            is State.Loaded -> {
                val filteredMovies = if (genre.isEmpty()) {
                    currentState.moviesList
                } else {
                    currentState.moviesList.filter { it.genres.any { genre in it } }
                }
                movies = filteredMovies
                selectedGenre.postValue(genre)
                d("lol", "selectedGenre.postValue(genre) ${selectedGenre.value}")
            }
            else -> {}
        }
    }

}