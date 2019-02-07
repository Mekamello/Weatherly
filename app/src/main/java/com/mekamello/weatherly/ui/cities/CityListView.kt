package com.mekamello.weatherly.ui.cities

import io.reactivex.Observable

interface CityListView {
    fun refreshIntent(): Observable<Unit>
    fun dialogIntent(): Observable<Unit>
    fun addCityIntent(): Observable<String>
    fun detailIntent(): Observable<Int>
    fun render(viewState: CityListViewState)
}