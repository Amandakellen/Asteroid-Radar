package com.example.asteroid.data

data class NeoResponse(
    val links: Links,
    val element_count: Int,
    val near_earth_objects: Map<String, List<NearEarthObject>>
)
