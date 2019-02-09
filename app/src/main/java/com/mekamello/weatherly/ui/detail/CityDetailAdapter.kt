package com.mekamello.weatherly.ui.detail

import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.util.SortedListAdapterCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mekamello.weatherly.R
import com.mekamello.weatherly.ui.resources.WeatherIconResourceProvider
import javax.inject.Inject

class CityDetailAdapter
@Inject constructor(
        private val iconResourceProvider: WeatherIconResourceProvider
) : RecyclerView.Adapter<CityDetailViewHolder>() {
    private val callback = object : SortedListAdapterCallback<DailyItem>(this) {
        override fun areItemsTheSame(p0: DailyItem, p1: DailyItem): Boolean = p0.dayOfWeek == p1.dayOfWeek
        override fun compare(p0: DailyItem, p1: DailyItem): Int = p0.date.compareTo(p1.date)
        override fun areContentsTheSame(p0: DailyItem, p1: DailyItem): Boolean = p0 == p1
    }
    private val list = SortedList<DailyItem>(DailyItem::class.java, callback)

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): CityDetailViewHolder =
        CityDetailViewHolder(inflate(container))

    override fun getItemCount(): Int = list.size()

    override fun onBindViewHolder(holder: CityDetailViewHolder, position: Int) {
        val item = list[position]
        val icon = iconResourceProvider.getIcon(item.weatherId)
        holder.bind(item, icon)
    }

    fun upload(content: List<DailyItem>) {
        list.replaceAll(content)
    }

    private fun inflate(container: ViewGroup): View =
        LayoutInflater
            .from(container.context)
            .inflate(R.layout.item_daily, container, false)
}