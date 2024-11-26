package com.example.asteroid.api

import android.util.Log
import com.example.asteroid.Asteroid
import com.example.asteroid.Constants
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun parseAsteroidsJsonResult(jsonResult: JSONObject): ArrayList<Asteroid> {
    try {
        // Access the array of "near_earth_objects"
        val nearEarthObjectsArray = jsonResult.getJSONArray("near_earth_objects")

        val asteroidList = ArrayList<Asteroid>()

        // Iterate over each asteroid in the array
        for (i in 0 until nearEarthObjectsArray.length()) {
            val asteroidJson = nearEarthObjectsArray.getJSONObject(i)
            val id = asteroidJson.getLong("id")
            val codename = asteroidJson.getString("name")
            val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
            val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
                .getJSONObject("kilometers").getDouble("estimated_diameter_max")

            // Check if there are approach data
            if (asteroidJson.has("close_approach_data") && asteroidJson.getJSONArray("close_approach_data").length() > 0) {
                val closeApproachData = asteroidJson
                    .getJSONArray("close_approach_data").getJSONObject(0)

                val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
                    .getDouble("kilometers_per_second")
                val distanceFromEarth = closeApproachData.getJSONObject("miss_distance")
                    .getDouble("astronomical")

                // Indicate if the asteroid is potentially hazardous
                val isPotentiallyHazardous = asteroidJson
                    .getBoolean("is_potentially_hazardous_asteroid")

                // Create the Asteroid object and add it to the list
                val asteroid = Asteroid(
                    id,
                    codename,
                    closeApproachData.getString("close_approach_date"), // Date of the approach
                    absoluteMagnitude,
                    estimatedDiameter,
                    relativeVelocity,
                    distanceFromEarth,
                    isPotentiallyHazardous
                )
                asteroidList.add(asteroid)
            }
        }

        return asteroidList

    } catch (e: Exception) {
        Log.e("AsteroidParsing", "Error parsing JSON", e)
        throw e
    }
}

private fun getNextSevenDaysFormattedDates(): ArrayList<String> {
    val formattedDateList = ArrayList<String>()

    val calendar = Calendar.getInstance()
    for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        formattedDateList.add(dateFormat.format(currentTime))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    return formattedDateList
}
