package com.qifan.thepsetest.app.base.viewmodel

import com.qifan.thepsetest.extension.reactive.computation
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

interface IViewState {

    enum class State {
        INIT,
        LOADING,
        SUCCESS,
        ERROR
    }
}


class LoadingViewModelState<T> : IViewState {
    private val stateSink: BehaviorProcessor<State<T>> =
        BehaviorProcessor.createDefault(State(IViewState.State.INIT))

    private val state = stateSink
        .computation()

    val loading: Flowable<Boolean> = state
        .map { it.status == IViewState.State.LOADING }
        .distinctUntilChanged()

    val hasError: Flowable<Pair<Boolean, Throwable?>> = state
        .map { (it.status == IViewState.State.ERROR) to (it.error) }
        .distinctUntilChanged()

    val error: Flowable<Pair<Boolean, Throwable?>> = hasError
        .filter { (hasError, _) -> hasError }


    val success: Flowable<T> = state
        .map { (it.status == IViewState.State.SUCCESS) to it.data }
        .distinctUntilChanged()
        .filter { (isSuccess, data) -> isSuccess && data != null }
        .map { (_, data) -> data!! }

    fun onSuccess(data: T) = stateSink.onNext(State(IViewState.State.SUCCESS, data, null))
    fun onError(throwable: Throwable) =
        stateSink.onNext(State(IViewState.State.ERROR, null, throwable))

    fun onLoading() = stateSink.onNext(State(IViewState.State.LOADING, null, null))
    fun reset() = stateSink.onNext(State(IViewState.State.INIT, null, null))


    data class State<T>(
        val status: IViewState.State,
        val data: T? = null,
        val error: Throwable? = null
    )
}