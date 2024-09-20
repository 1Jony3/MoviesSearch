package com.example.moviessearch.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.moviessearch.R
import com.example.moviessearch.databinding.GenresItemBinding
import com.example.moviessearch.model.data.entities.Movie

interface OnGenreClickListener {
    var selectedGenre: String?
    fun onGenreClick(genre: String, position: Int)
}
class GenresAdapter(private val onClickListener: OnGenreClickListener, private val genres: List<String>, private var selectedGenre: String? = null): RecyclerView.Adapter<GenresAdapter.GenresHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GenresItemBinding.inflate(inflater, parent, false)
        return GenresHolder(binding)
    }

    override fun onBindViewHolder(holder: GenresHolder, position: Int) {
        val genre = genres[position]
        with(holder.binding) {
            textGenre.text = genre.replaceFirstChar { it.titlecase() }

            genreLinearLayout.setOnClickListener {
                onClickListener.onGenreClick(genre, position)
            }
            genreLinearLayout.setOnClickListener{ onClickListener.onGenreClick(genre, position) }

            if (genre == selectedGenre || genre == onClickListener.selectedGenre) {
                genreLinearLayout.setBackgroundColor(ContextCompat.getColor(genreLinearLayout.context, R.color.yellow))
            } else {
                genreLinearLayout.setBackgroundColor(ContextCompat.getColor(genreLinearLayout.context, R.color.colorBackground))
            }
            val layoutParams: StaggeredGridLayoutManager.LayoutParams = holder.itemView.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = true

        }
    }

    override fun getItemCount() = genres.size

    class GenresHolder(val binding: GenresItemBinding) : RecyclerView.ViewHolder(binding.root)
}