package com.example.presentation.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.R

class ShimmerAdapter(private val itemCount: Int) : RecyclerView.Adapter<ShimmerAdapter.ShimmerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShimmerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_shimmer, parent, false)
        return ShimmerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShimmerViewHolder, position: Int) {

    }

    override fun getItemCount() = itemCount

    class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
