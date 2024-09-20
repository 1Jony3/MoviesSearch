package com.example.moviessearch.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.example.moviessearch.databinding.TitleItemBinding

class TitleAdapter(private val titleString: String): RecyclerView.Adapter<TitleAdapter.TitleHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TitleItemBinding.inflate(inflater, parent, false)
        return TitleHolder(binding)
    }

    override fun onBindViewHolder(holder: TitleHolder, position: Int) {
        with(holder.binding) {
            title.text = titleString
            val layoutParams: StaggeredGridLayoutManager.LayoutParams = holder.itemView.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = true
        }
    }

    override fun getItemCount() = 1

    class TitleHolder(val binding: TitleItemBinding) : RecyclerView.ViewHolder(binding.root)
}