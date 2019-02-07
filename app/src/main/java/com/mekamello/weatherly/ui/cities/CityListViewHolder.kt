package com.mekamello.weatherly.ui.cities

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mekamello.weatherly.R
import com.mekamello.weatherly.domain.models.CityMain
import io.reactivex.subjects.PublishSubject

class CityListViewHolder(
    view: View,
    private val clicks: PublishSubject<Int>
) : RecyclerView.ViewHolder(view) {
    companion object {
        private const val NO_ID = -1
    }
    private val name = itemView.findViewById<TextView>(R.id.city_item_name)
    private val weather = itemView.findViewById<TextView>(R.id.city_item_weather)
    private val temperature = itemView.findViewById<TextView>(R.id.city_item_temp)
    private var id = NO_ID

    fun bind(city: CityMain){
        id = city.city.id
        name.text = city.city.name
        weather.text = city.weatherDescription
        temperature.text = if(city.temp > 0F) {
            "+${city.temp}"
        } else {
            city.temp.toString()
        }
        itemView.setOnClickListener { clicks.onNext(id) }
    }

    fun unbind(){
        id = NO_ID
        itemView.setOnClickListener(null)
    }
}