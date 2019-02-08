package com.mekamello.weatherly.ui.detail

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mekamello.weatherly.R

class CityDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = itemView.findViewById(R.id.daily_item_name)
    val weather: TextView = itemView.findViewById(R.id.daily_item_weather)
    val min: TextView = itemView.findViewById(R.id.daily_item_temp_min)
    val max: TextView = itemView.findViewById(R.id.daily_item_temp_max)
    val icon: ImageView = itemView.findViewById(R.id.daily_item_icon)

    fun bind(daily: DailyItem) {
        min.text = daily.getMinTempString()
        max.text = daily.getMaxTempString()
        weather.text = daily.weatherDescription
        name.text = daily.dayOfWeek
    }
}