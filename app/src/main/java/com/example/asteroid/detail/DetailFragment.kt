package com.example.asteroid.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.asteroid.R
import androidx.appcompat.app.AlertDialog
import com.example.asteroid.data.AsteroidImage
import com.example.asteroid.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val asteroid = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid

        binding.asteroid = asteroid

        val imageResource = getImage(asteroid.isPotentiallyHazardous)
        addDescription(asteroid.isPotentiallyHazardous, binding.activityMainImageOfTheDay)
        Picasso.get()
            .load(imageResource)
            .into(binding.activityMainImageOfTheDay)

        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

        return binding.root
    }

    private fun addDescription(isPotentiallyHazardous: Boolean, imageView: ImageView) {
        imageView.contentDescription =  if (isPotentiallyHazardous) {
                "Image of an angry asteroid because this asteroid is Potentially Hazardous"
        }else{
            "Image of an happy asteroid because this asteroid is not Potentially Hazardous"
        }
    }

    private fun getImage(isPotentiallyHazardous: Boolean): Int {
        return if (isPotentiallyHazardous) {
            AsteroidImage.HAZARDOUS.imageResId
        } else {
            AsteroidImage.SAFE.imageResId
        }

    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
