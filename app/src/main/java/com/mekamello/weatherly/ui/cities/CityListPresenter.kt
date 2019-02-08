package com.mekamello.weatherly.ui.cities

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface CityListPresenter {
    fun onCreate(view: CityListView)
    fun onDestroy()
}

class CityListPresenterImpl
@Inject constructor(
    private val interactor: CityListInteractor
) : CityListPresenter {
    private val compositeDisposable = CompositeDisposable()
    private var listView: CityListView? = null

    override fun onCreate(view: CityListView) {
        listView = view
        listView?.let {
            preload()
            subscribeOnCityIntent(it.addCityIntent())
            subscribeOnRefreshIntent(it.refreshIntent())
            subscribeOnShowDialogIntent(it.dialogIntent())
            subscribeOnCityDetailIntent(it.detailIntent())
        }
    }
    override fun onDestroy() {
        compositeDisposable.clear()
        listView = null
    }
    private fun preload(){
        compositeDisposable += interactor.getCityList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { listView?.render(it) }
    }
    private fun subscribeOnCityIntent(intent: Observable<String>) {
        compositeDisposable += intent
            .debounce(1, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .switchMap { interactor.addCity(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { listView?.render(it) },
                onError = { Log.e("CityListPresenter", it.message) }
            )
    }
    private fun subscribeOnRefreshIntent(intent: Observable<Unit>) {
        compositeDisposable += intent
            .observeOn(Schedulers.io())
            .switchMap { interactor.getCityList() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { listView?.render(it) },
                onError = { Log.e("CityListPresenter", it.message) }
            )
    }
    private fun subscribeOnShowDialogIntent(intent: Observable<Unit>){
        compositeDisposable += intent
            .map { CityListViewState.ShowDialog }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { listView?.render(it) }
    }
    private fun subscribeOnCityDetailIntent(intent: Observable<Int>){
        compositeDisposable+= intent
            .map { CityListViewState.ShowDetail(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { listView?.render(it) }
    }

}