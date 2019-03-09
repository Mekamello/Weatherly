package com.mekamello.weatherly.base

import io.reactivex.Observable

interface MviView<A: Action, S> {
    val actions: Observable<A>
    fun render(state: S)
}