package com.example.moviessearch.model.adapter

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.moviessearch.R
import com.example.moviessearch.databinding.MovieItemBinding
import com.example.moviessearch.model.data.entities.Movie

interface OnMovieClickListener {
    fun onMovieClick(movie: Movie)
}

class MoviesAdapter(private val onClickListener: OnMovieClickListener, private val  moviesList: List<Movie>): RecyclerView.Adapter<MoviesAdapter.MoviesHolder>() {

    private var movies: List<Movie>? = moviesList

    fun filterMovies(moviesByGenre: List<Movie>?) {
        movies = moviesByGenre ?: moviesList
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return MoviesHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        val movie = movies?.get(position)

        with(holder.binding) {
            titleMovie.text = movie!!.localizedName
            movie.imageUrl.let {
                Glide.with(imageMovie)
                    .load(it)
                    .error(R.drawable.no_image)
                    .placeholder(R.drawable.no_image)
                    .into(imageMovie)

            }
            d("lol", "aaadappptteeerrr  $movie")
            moviesLinearLayout.setOnClickListener{ onClickListener.onMovieClick(movie) }

            val layoutParams: StaggeredGridLayoutManager.LayoutParams = holder.itemView.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = false
        }

    }

    override fun getItemCount() = movies!!.size

    class MoviesHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

}
