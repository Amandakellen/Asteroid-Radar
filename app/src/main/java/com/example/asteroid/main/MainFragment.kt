package com.example.asteroid.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.asteroid.data.Asteroid
import com.example.asteroid.R
import com.example.asteroid.data.AsteroidFilter
import com.example.asteroid.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var asteroidAdapter: AsteroidAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.asteroidRecycler.layoutManager = LinearLayoutManager(context)
        asteroidAdapter = AsteroidAdapter(emptyList()) { asteroid ->
            onAsteroidClicked(asteroid)
        }
        binding.asteroidRecycler.adapter = asteroidAdapter

        viewModel.asteroids.observe(viewLifecycleOwner, Observer { asteroids ->
            asteroidAdapter = AsteroidAdapter(asteroids) { asteroid ->
                onAsteroidClicked(asteroid)
            }
            binding.asteroidRecycler.adapter = asteroidAdapter
        })

        viewModel.pictureOfDayUrl.observe(viewLifecycleOwner, Observer { url ->
            if (url != null) {
                loadImage(url, binding.activityMainImageOfTheDay)
            }
        })

        val toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun onAsteroidClicked(asteroid: Asteroid) {
        val action = MainFragmentDirections.actionShowDetail(asteroid)
        findNavController().navigate(action)
    }


    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_all_menu -> viewModel.setFilter(AsteroidFilter.ALL)
            R.id.show_rent_menu -> viewModel.setFilter(AsteroidFilter.TODAY)
            R.id.show_buy_menu -> viewModel.setFilter(AsteroidFilter.WEEK)
        }
        return true
    }


    fun loadImage(url: String, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }
}
