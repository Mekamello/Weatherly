package com.mekamello.weatherly.ui.detail

import io.reactivex.Observable

interface CityDetailView {
    fun refreshIntent(): Observable<Unit>
    fun render(viewState: CityDetailViewState)
}