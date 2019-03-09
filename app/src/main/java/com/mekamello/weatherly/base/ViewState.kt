package com.mekamello.weatherly.base

interface ViewState {
    fun diff(state: ViewState): Boolean
}
