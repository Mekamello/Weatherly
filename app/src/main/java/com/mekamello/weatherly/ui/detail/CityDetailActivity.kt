package com.mekamello.weatherly.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.mekamello.weatherly.R
import com.mekamello.weatherly.WeatherlyApplication
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class CityDetailActivity : AppCompatActivity(), CityDetailView {
    companion object {
        private const val EXTRA_CITY_ID: String = "EXTRA_CITY_ID"
        fun start(context: Context, cityId: Int) {
            val intent = Intent(context, CityDetailActivity::class.java)
            intent.putExtra(EXTRA_CITY_ID, cityId)
            context.startActivity(intent)
        }
    }
    @Inject
    lateinit var presenter: CityDetailPresenter
    @Inject
    lateinit var adapter: CityDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupInjection()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val decorator = DividerItemDecoration(this, layoutManager.orientation)
        city_detail_list.adapter = adapter
        city_detail_list.layoutManager = layoutManager
        city_detail_list.addItemDecoration(decorator)
        presenter.onCreate(this)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private fun setupInjection() {
        WeatherlyApplication.component
            .plus(CityDetailModule(this, intent.getIntExtra(EXTRA_CITY_ID, -1)))
            .inject(this)
    }

    override fun refreshIntent(): Observable<Unit> =
        RxSwipeRefreshLayout.refreshes(refresher)
            .map { Unit }

    override fun render(viewState: CityDetailViewState) = when (viewState) {
        is CityDetailViewState.Content -> renderContent(viewState)
        is CityDetailViewState.Error -> renderError(viewState)
        is CityDetailViewState.Loading -> renderLoading()
    }

    private fun renderContent(viewState: CityDetailViewState.Content) {
        refresher.isRefreshing = false
        title = viewState.city.name
        adapter.upload(viewState.dailies)
    }

    private fun renderError(viewState: CityDetailViewState.Error) {
        refresher.isRefreshing = false
        Toast.makeText(this, viewState.throwable.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    private fun renderLoading() {
        refresher.isRefreshing = true
    }
}