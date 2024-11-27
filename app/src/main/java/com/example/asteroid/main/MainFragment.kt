package com.example.asteroid.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asteroid.Asteroid
import com.example.asteroid.R
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

        // Configurando o RecyclerView
        binding.asteroidRecycler.layoutManager = LinearLayoutManager(context)
        asteroidAdapter = AsteroidAdapter(emptyList()) { asteroid ->
            // Ação quando um item é clicado
            onAsteroidClicked(asteroid)
        }
        binding.asteroidRecycler.adapter = asteroidAdapter

        // Observar os asteroides
        viewModel.asteroids.observe(viewLifecycleOwner, Observer { asteroids ->
            asteroidAdapter = AsteroidAdapter(asteroids) { asteroid ->
                onAsteroidClicked(asteroid)
            }
            binding.asteroidRecycler.adapter = asteroidAdapter
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun onAsteroidClicked(asteroid: Asteroid) {
        // Ação ao clicar no item (por exemplo, abrir uma nova tela com os detalhes do asteroide)
        // Você pode adicionar a navegação aqui
        // Exemplo: navegar para uma tela de detalhes
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
