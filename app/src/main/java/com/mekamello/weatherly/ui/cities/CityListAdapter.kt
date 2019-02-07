package com.mekamello.weatherly.ui.cities

import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.util.SortedListAdapterCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mekamello.weatherly.R
import com.mekamello.weatherly.domain.models.CityMain
import io.reactivex.subjects.PublishSubject

class CityListAdapter : RecyclerView.Adapter<CityListViewHolder>() {
    private val callback = object : SortedListAdapterCallback<CityMain>(this) {
        override fun areItemsTheSame(p0: CityMain, p1: CityMain): Boolean = p0.city.id == p1.city.id
        override fun compare(p0: CityMain, p1: CityMain): Int = p0.city.id.compareTo(p1.city.id)
        override fun areContentsTheSame(p0: CityMain, p1: CityMain): Boolean = p0 == p1
    }
    private val list = SortedList<CityMain>(CityMain::class.java, callback)
    private val clicks: PublishSubject<Int> = PublishSubject.create()

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): CityListViewHolder =
        CityListViewHolder(inflate(container), clicks)

    override fun getItemCount(): Int = list.size()

    override fun onBindViewHolder(holder: CityListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onViewRecycled(holder: CityListViewHolder) {
        holder.unbind()
        super.onViewRecycled(holder)
    }

    fun upload(content: List<CityMain>) {
        list.addAll(content)
    }

    fun clicks() = clicks

    private fun inflate(container: ViewGroup): View =
        LayoutInflater
            .from(container.context)
            .inflate(R.layout.item_city, container, false)
}

