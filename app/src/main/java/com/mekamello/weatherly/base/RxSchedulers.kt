package com.mekamello.weatherly.base

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface RxSchedulers {
    fun ui(): Scheduler
    fun io(): Scheduler
    fun computation(): Scheduler
}

class RxSchedulersImpl() : RxSchedulers {
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    override fun io(): Scheduler = Schedulers.io()

    override fun computation(): Scheduler = Schedulers.computation()
}