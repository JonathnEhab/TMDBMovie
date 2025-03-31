package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.models.MovieDomainModel
import com.example.presentation.databinding.ItemMovieBinding


class MoviesAdapter(
    private val onItemClick: (Int) -> Unit
) : ListAdapter<MovieDomainModel, MoviesAdapter.ViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val itemBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(category: MovieDomainModel) {
            itemBinding.titleTxt.text = category.title
            itemBinding.scoreTxt.text = category.voteAverage.toString()

            Glide.with(itemBinding.root.context)
                .load(category.posterPath)
                .into(itemBinding.pic)

            itemBinding.root.setOnClickListener {
                onItemClick(category.id)
            }
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<MovieDomainModel>() {
        override fun areItemsTheSame(oldItem: MovieDomainModel, newItem: MovieDomainModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieDomainModel, newItem: MovieDomainModel): Boolean {
            return oldItem == newItem
        }
    }
}
