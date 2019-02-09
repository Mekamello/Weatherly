package com.mekamello.weatherly.ui.detail

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface CityDetailPresenter {
    fun onCreate(view: CityDetailView)
    fun onDestroy()
}

class CityDetailPresenterImpl
@Inject constructor(
    private val interactor: CityDetailInteractor,
    private val cityId: Int
) : CityDetailPresenter {
    private val compositeDisposable = CompositeDisposable()
    private var detailView: CityDetailView? = null

    override fun onCreate(view: CityDetailView) {
        detailView = view
        subscribeOnRefreshIntent(view.refreshIntent())
    }

    override fun onDestroy() {
        detailView = null
        compositeDisposable.clear()
    }

    private fun subscribeOnRefreshIntent(intent: Observable<Unit>) {
        compositeDisposable += intent
            .observeOn(Schedulers.io())
            .switchMap { interactor.getDetail(cityId) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { preloadContent() }
            .subscribeBy(
                onNext = { detailView?.render(it) },
                onError = { Log.e("CityDetailPresenter", it.message) }
            )
    }

    private fun preloadContent() {
        compositeDisposable += interactor.getDetail(cityId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { detailView?.render(it) },
                onError = { Log.e("CityDetailPresenter", it.message) }
            )
    }
}