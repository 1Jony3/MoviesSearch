package com.example.moviessearch.screen

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.moviessearch.R
import com.example.moviessearch.databinding.FragmentListMoviesBinding
import com.example.moviessearch.model.adapter.GenresAdapter
import com.example.moviessearch.model.adapter.MoviesAdapter
import com.example.moviessearch.model.adapter.OnGenreClickListener
import com.example.moviessearch.model.adapter.OnMovieClickListener
import com.example.moviessearch.model.adapter.TitleAdapter
import com.example.moviessearch.model.data.entities.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListMoviesFragment : Fragment(R.layout.fragment_list_movies) {

    private lateinit var binding: FragmentListMoviesBinding
    private val viewModel: ListMoviesViewModel by viewModel()
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var genresAdapter: GenresAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            setupObservers()
        }
    }

    private fun updateUI(state: ListMoviesViewModel.State) {
        binding.loadStateView.progressBar.visibility =
            if (state == ListMoviesViewModel.State.Loading) View.VISIBLE else View.GONE
        binding.list.visibility =
            if (state is ListMoviesViewModel.State.Loaded) View.VISIBLE else View.GONE
        binding.loadStateView.errorRelativeLayout.visibility =
        if (state is ListMoviesViewModel.State.Error) View.VISIBLE else View.GONE

        binding.loadStateView.retryText.setOnClickListener {
            onClickError()
        }
        setAdapters(state)
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            updateUI(state)
        }
    }

    private fun onClickError() {
        viewModel.getMovies()
    }

    private val movieClickListener = object : OnMovieClickListener {
        override fun onMovieClick(movie: Movie) {
            openDetails(movie)
        }
    }

    private val genreClickListener = object : OnGenreClickListener {
        override var selectedGenre: String? = null
        override fun onGenreClick(genre: String, position: Int) {
            if (selectedGenre == genre) {
                selectedGenre = null
                viewModel.toggleGenreFilter("")
            } else {
                selectedGenre = genre
                viewModel.toggleGenreFilter(genre)
            }
            moviesAdapter.filterMovies(viewModel.movies)
            genresAdapter.notifyDataSetChanged()
        }
    }

    private fun setupAdapter() {
        val adapter = ConcatAdapter(
            TitleAdapter("Жанры"),
            genresAdapter,
            TitleAdapter("Фильмы"),
            moviesAdapter
        )

        binding.list.apply {
            this.adapter = adapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        }
    }


    private fun setAdapters(state: ListMoviesViewModel.State) {
        if (state is ListMoviesViewModel.State.Loaded) {
            moviesAdapter = MoviesAdapter(movieClickListener, viewModel.movies)
            genresAdapter = GenresAdapter(genreClickListener, viewModel.genres, viewModel.selectedGenre.value)
            setupAdapter()
        }
    }

    private fun openDetails(movie: Movie) {
        findNavController().navigate(
            R.id.action_listFilmsFragment_to_detailsFilmsFragment,
            bundleOf(ARG_MOVIE to movie, ARG_MOVIE_NAME to movie.name)
        )
    }
    companion object {
        const val ARG_MOVIE_NAME = "nameMovie"
        const val ARG_MOVIE = "movie"
    }
}




