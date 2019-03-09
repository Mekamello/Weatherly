package com.mekamello.weatherly.base

import io.reactivex.Observable

interface Middleware<A: Action, S> {
    fun bind(actions: Observable<A>, state: Observable<S>): Observable<A>
}