package com.mekamello.weatherly.base

interface Reducer<S, A: Action> {
    fun reduce(state: S, action: A): S
}