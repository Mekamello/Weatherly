package com.mekamello.weatherly.base

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class Store<A: Action, S>(
    private val reducer: Reducer<S, A>,
    private val middlewares: List<Middleware<A, S>>,
    private val initialState: S,
    private val schedulers: RxSchedulers
) {
    private val state: BehaviorSubject<S> = BehaviorSubject.createDefault(initialState)
    private val actions: PublishSubject<A> = PublishSubject.create()
    fun wire(): Disposable {
        val disposable = CompositeDisposable()
        disposable += actions
            .withLatestFrom(state) { action, state -> reducer.reduce(state, action) }
            .distinctUntilChanged()
            .subscribe(state::onNext)
        disposable += Observable.merge<A>(middlewares.map { it.bind(actions, state) })
            .subscribe(actions::onNext)
        return disposable
    }

    fun bind(view: MviView<A, S>): Disposable {
        val disposable = CompositeDisposable()
        disposable += state.observeOn(schedulers.ui()).subscribe(view::render)
        disposable += view.actions.subscribe(actions::onNext)
        return disposable
    }
}