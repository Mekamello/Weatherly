package com.mekamello.weatherly.ui.cities

import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.util.SortedListAdapterCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mekamello.weatherly.R
import com.mekamello.weatherly.domain.models.CityShortWeather
import com.mekamello.weatherly.ui.resources.WeatherIconResourceProvider
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class CityListAdapter
@Inject constructor(
    private val iconResourceProvider: WeatherIconResourceProvider
) : RecyclerView.Adapter<CityListViewHolder>() {
    private val callback = object : SortedListAdapterCallback<CityShortWeather>(this) {
        override fun areItemsTheSame(p0: CityShortWeather, p1: CityShortWeather): Boolean = p0.city.id == p1.city.id
        override fun compare(p0: CityShortWeather, p1: CityShortWeather): Int = p0.city.id.compareTo(p1.city.id)
        override fun areContentsTheSame(p0: CityShortWeather, p1: CityShortWeather): Boolean = p0 == p1
    }
    private val list = SortedList<CityShortWeather>(CityShortWeather::class.java, callback)
    private val clicks: PublishSubject<Int> = PublishSubject.create()

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): CityListViewHolder =
        CityListViewHolder(inflate(container), clicks)

    override fun getItemCount(): Int = list.size()

    override fun onBindViewHolder(holder: CityListViewHolder, position: Int) {
        val item = list[position]
        val icon = iconResourceProvider.getIcon(item.weatherId)
        holder.bind(item, icon)
    }

    override fun onViewRecycled(holder: CityListViewHolder) {
        holder.unbind()
        super.onViewRecycled(holder)
    }

    fun upload(content: List<CityShortWeather>) {
        list.addAll(content)
    }

    fun clicks() = clicks

    private fun inflate(container: ViewGroup): View =
        LayoutInflater
            .from(container.context)
            .inflate(R.layout.item_city, container, false)
}

