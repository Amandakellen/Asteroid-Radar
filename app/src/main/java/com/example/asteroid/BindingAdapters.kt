package com.example.asteroid

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.asteroid.data.Asteroid
import java.util.Calendar

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
@BindingAdapter("isLoading")
fun setIsLoading(view: View, isLoading: Boolean?) {
    view.visibility = if (isLoading == true) View.VISIBLE else View.GONE
}

@BindingAdapter("datesContainingCurrentYearOrLast")
fun bindDatesContainingCurrentYearOrLast(textView: TextView, dateList: List<String>?) {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
    val filteredDates = dateList?.filter { it.contains(currentYear) }

    val finalDates = if (filteredDates.isNullOrEmpty()) {
        dateList?.lastOrNull()?.let { listOf(it) }
    } else {
        filteredDates
    }
    textView.text = finalDates?.joinToString(", ") ?: "Nenhuma data disponível"
}
@BindingAdapter("lastCloseApproachDate")
fun bindLastCloseApproachDate(textView: TextView, dateList: List<String>?) {
    dateList?.lastOrNull()?.let { lastDate ->
        textView.text = lastDate
    } ?: run {
        textView.text = ""
    }
}