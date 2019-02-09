package com.mekamello.weatherly.ui.cities

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding3.view.clicks
import com.mekamello.weatherly.R
import com.mekamello.weatherly.WeatherlyApplication
import com.mekamello.weatherly.ui.detail.CityDetailActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class CityListActivity : AppCompatActivity(), CityListView {
    @Inject
    lateinit var presenter: CityListPresenter
    @Inject
    lateinit var adapter: CityListAdapter
    private val cityIntent: PublishSubject<String> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.title_cities_list)
        setupInjection()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val decorator = DividerItemDecoration(this, layoutManager.orientation)
        city_list.adapter = adapter
        city_list.layoutManager = layoutManager
        city_list.addItemDecoration(decorator)
        presenter.onCreate(this)
    }

    private fun setupInjection(){
        WeatherlyApplication.component
            .plus(CityListModule(this))
            .inject(this)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun refreshIntent(): Observable<Unit> =
        RxSwipeRefreshLayout.refreshes(refresher)
            .map { Unit }

    override fun addCityIntent(): Observable<String> = cityIntent

    override fun dialogIntent(): Observable<Unit> = city_add.clicks()

    override fun detailIntent(): Observable<Int> = adapter.clicks()

    override fun render(viewState: CityListViewState) = when (viewState) {
        is CityListViewState.Loading -> renderStartLoading()
        is CityListViewState.Content -> renderContent(viewState)
        is CityListViewState.Error -> renderError(viewState)
        is CityListViewState.ShowDialog -> renderShowDialog()
        is CityListViewState.ShowDetail -> renderShowDetail(viewState)
    }

    private fun renderStartLoading() {
        refresher.isRefreshing = true
    }

    private fun renderContent(viewState: CityListViewState.Content) {
        refresher.isRefreshing = false
        adapter.upload(viewState.cities)
    }

    private fun renderError(viewState: CityListViewState.Error) {
        refresher.isRefreshing = false
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
    }

    private fun renderShowDialog() {
        val editText = EditText(this)
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Enter Your Title")
        alert.setView(editText)
        alert.setPositiveButton("Ok") { _, _ -> cityIntent.onNext(editText.text.toString()) }
        alert.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        alert.show()
    }

    private fun renderShowDetail(viewState: CityListViewState.ShowDetail){
        CityDetailActivity.start(this, viewState.id)
    }
}
