

package com.example.asteroid.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroid.Asteroid
import com.example.asteroid.databinding.AsteroidItemBinding


class AsteroidAdapter(
    private val asteroidList: List<Asteroid>,
    private val onItemClick: (Asteroid) -> Unit
) : RecyclerView.Adapter<AsteroidAdapter.AsteroidViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val binding = AsteroidItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = asteroidList[position]
        holder.bind(asteroid)
        holder.itemView.setOnClickListener { onItemClick(asteroid) }
    }

    override fun getItemCount(): Int = asteroidList.size

    class AsteroidViewHolder(private val binding: AsteroidItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }
    }
}
