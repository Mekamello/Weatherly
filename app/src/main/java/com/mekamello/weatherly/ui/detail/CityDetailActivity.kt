package com.mekamello.weatherly.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.mekamello.weatherly.R
import com.mekamello.weatherly.WeatherlyApplication
import com.mekamello.weatherly.base.MviView
import com.mekamello.weatherly.base.Store
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class CityDetailActivity : AppCompatActivity(), MviView<CityDetailAction, CityDetailViewState> {
    companion object {
        private const val EXTRA_CITY_ID: String = "EXTRA_CITY_ID"
        fun start(context: Context, cityId: Int) {
            val intent = Intent(context, CityDetailActivity::class.java)
            intent.putExtra(EXTRA_CITY_ID, cityId)
            context.startActivity(intent)
        }
    }

    private val userActions: PublishSubject<CityDetailAction> = PublishSubject.create()
    override val actions: Observable<CityDetailAction> = userActions
    @Inject
    lateinit var store: Store<CityDetailAction, CityDetailViewState>
    @Inject
    lateinit var adapter: CityDetailAdapter

    private lateinit var viewBindings: Disposable
    private lateinit var wireBindings: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupInjection()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val decorator = DividerItemDecoration(this, layoutManager.orientation)
        city_detail_list.adapter = adapter
        city_detail_list.layoutManager = layoutManager
        city_detail_list.addItemDecoration(decorator)
        refresher.setOnRefreshListener { userActions.onNext(CityDetailAction.Refresh) }
        wireBindings = store.wire()
        viewBindings = store.bind(this)
        userActions.onNext(CityDetailAction.Refresh)
    }

    override fun onDestroy() {
        wireBindings.dispose()
        viewBindings.dispose()
        super.onDestroy()
    }

    private fun setupInjection() {
        WeatherlyApplication.component
            .plus(CityDetailModule(this, intent.getIntExtra(EXTRA_CITY_ID, -1)))
            .inject(this)
    }

    override fun render(state: CityDetailViewState) {
        refresher.isRefreshing = state.loading
        state.city?.let { title = it.name }
        state.throwable?.let { renderError(it) }
        adapter.upload(state.dailies)
    }

    private fun renderError(throwable: Throwable) {
        refresher.isRefreshing = false
        Toast.makeText(this, throwable.localizedMessage, Toast.LENGTH_SHORT).show()
    }

}