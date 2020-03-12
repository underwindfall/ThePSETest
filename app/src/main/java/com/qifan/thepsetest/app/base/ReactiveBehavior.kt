package com.qifan.thepsetest.app.base

import io.reactivex.disposables.CompositeDisposable

interface ReactiveBehavior {
    val compositeDisposable: CompositeDisposable
}

class ReactiveBehaviorDelegate : ReactiveBehavior {
    override val compositeDisposable: CompositeDisposable
        get() = CompositeDisposable()
}