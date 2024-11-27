package com.example.asteroid

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.asteroid.data.Asteroid

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

@BindingAdapter("lastItem")
fun setLastItem(imageView: ImageView, list: List<Asteroid>?) {
    // Verifica se a lista não é nula e tem itens, e então pega o último item
    list?.lastOrNull()?.let {
        // Aqui você pode configurar a lógica para o que deseja exibir com base no último item
        // Por exemplo, vamos setar uma imagem condicionalmente com base no item
        val asteroidImage = if (it.isPotentiallyHazardous) {
            R.drawable.asteroid_hazardous
        } else {
            R.drawable.asteroid_safe
        }
        imageView.setImageResource(asteroidImage)
    }
}