package com.example.asteroid

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroid.databinding.AsteroidItemBinding
import com.example.asteroid.Asteroid

class AsteroidAdapter : RecyclerView.Adapter<AsteroidAdapter.AsteroidViewHolder>() {

    private var asteroidList: List<Asteroid> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Asteroid>) {
        asteroidList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val binding = AsteroidItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = asteroidList[position]
        holder.bind(asteroid)
    }

    override fun getItemCount() = asteroidList.size

    class AsteroidViewHolder(private val binding: AsteroidItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }
    }
}
