package com.qifan.thepsetest.app.base.viewmodel

import io.reactivex.Single

open class LoadingViewModel<T> : BaseViewModel(),
    IViewState by LoadingViewModelState<T>() {

   open val data: LoadingViewModelState<T> = LoadingViewModelState()

    fun <T> Single<T>.bindLoadingState(asyncOperationState: LoadingViewModelState<T>): Single<T> =
        doOnSubscribe { asyncOperationState.onLoading() }
            .doOnSuccess { asyncOperationState.onSuccess(it) }
            .doOnError { asyncOperationState.onError(it) }

}