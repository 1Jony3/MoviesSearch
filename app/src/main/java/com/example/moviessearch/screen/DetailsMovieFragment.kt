package com.example.moviessearch.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.moviessearch.R
import com.example.moviessearch.databinding.FragmentDetailsMovieBinding
import com.example.moviessearch.model.data.entities.Movie
import com.example.moviessearch.screen.ListMoviesFragment.Companion.ARG_MOVIE
import com.example.moviessearch.screen.ListMoviesFragment.Companion.ARG_MOVIE_NAME

class DetailsMovieFragment : Fragment(R.layout.fragment_details_movie) {

    private lateinit var binding: FragmentDetailsMovieBinding
    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movie = (arguments?.getSerializable(ARG_MOVIE) as? Movie)!!
        binding = FragmentDetailsMovieBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.movieTitle.text = movie.localizedName
        if (movie.genres.isEmpty()){
            binding.movieGenre.text = "${movie.year} год"
        }
        else binding.movieGenre.text = "${movie.genres.joinToString(", ")}, ${movie.year} год"
        binding.movieRating.text = movie.rating.toString()
        binding.movieDescription.text = movie.description

        movie.imageUrl.let {
            Glide.with(binding.imageMovie)
                .load(it)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(binding.imageMovie)

        }
    }
}