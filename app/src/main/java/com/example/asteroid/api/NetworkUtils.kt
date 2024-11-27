package com.example.asteroid.api

import android.util.Log
import com.example.asteroid.data.Asteroid
import com.example.asteroid.Constants
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun parseAsteroidsJsonResult(jsonResult: JSONObject, shouldFilterDates: Boolean = false): ArrayList<Asteroid> {
    try {
        val nearEarthObjectsArray = jsonResult.getJSONArray("near_earth_objects")
        val nextSevenDaysFormattedDates = getNextSevenDaysFormattedDates()
        val asteroidList = ArrayList<Asteroid>()
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        for (i in 0 until nearEarthObjectsArray.length()) {
            val asteroidJson = nearEarthObjectsArray.getJSONObject(i)
            val id = asteroidJson.getLong("id")
            val codename = asteroidJson.getString("name")
            val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
            val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
                .getJSONObject("kilometers").getDouble("estimated_diameter_max")

            if (asteroidJson.has("close_approach_data") && asteroidJson.getJSONArray("close_approach_data").length() > 0) {
                val closeApproachData = asteroidJson.getJSONArray("close_approach_data")
                val closeApproachDateList = mutableListOf<String>() // Mover a lista para o escopo correto

                for (data in 0 until closeApproachData.length()) {
                    val closeApproachDataItem = closeApproachData.getJSONObject(data)
                    val closeApproachDateItem = closeApproachDataItem.getString("close_approach_date")
                    closeApproachDateList.add(closeApproachDateItem)
                }

                closeApproachDateList.sortWith { date1, date2 ->
                    val parsedDate1 = dateFormatter.parse(date1)
                    val parsedDate2 = dateFormatter.parse(date2)
                    parsedDate1.compareTo(parsedDate2)
                }

                if (shouldFilterDates) {
                    val filteredDates = closeApproachDateList.filter { it in nextSevenDaysFormattedDates }
                    if (filteredDates.isNotEmpty()) {
                        val firstFilteredDate = filteredDates.first()
                        val relativeVelocity = closeApproachData.getJSONObject(0)
                            .getJSONObject("relative_velocity").getDouble("kilometers_per_second")
                        val distanceFromEarth = closeApproachData.getJSONObject(0)
                            .getJSONObject("miss_distance").getDouble("astronomical")
                        val isPotentiallyHazardous = asteroidJson
                            .getBoolean("is_potentially_hazardous_asteroid")

                        asteroidList.add(
                            Asteroid(
                                id,
                                codename,
                                filteredDates,
                                absoluteMagnitude,
                                estimatedDiameter,
                                relativeVelocity,
                                distanceFromEarth,
                                isPotentiallyHazardous
                            )
                        )
                    }
                } else {
                    val relativeVelocity = closeApproachData.getJSONObject(0)
                        .getJSONObject("relative_velocity").getDouble("kilometers_per_second")
                    val distanceFromEarth = closeApproachData.getJSONObject(0)
                        .getJSONObject("miss_distance").getDouble("astronomical")
                    val isPotentiallyHazardous = asteroidJson
                        .getBoolean("is_potentially_hazardous_asteroid")

                    asteroidList.add(
                        Asteroid(
                            id,
                            codename,
                            closeApproachDateList,
                            absoluteMagnitude,
                            estimatedDiameter,
                            relativeVelocity,
                            distanceFromEarth,
                            isPotentiallyHazardous
                        )
                    )
                }
            }
        }

        return asteroidList
    } catch (e: Exception) {
        Log.e("AsteroidParsing", "Error parsing JSON", e)
        throw e
    }
}



fun getNextSevenDaysFormattedDates(): ArrayList<String> {
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
