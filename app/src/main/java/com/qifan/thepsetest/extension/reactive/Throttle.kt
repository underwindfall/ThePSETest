package com.qifan.thepsetest.extension.reactive

import io.reactivex.Flowable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

private const val THROTTLE_DURATION = 1L

fun <T> Flowable<T>.throttleDefault(duration: Long = THROTTLE_DURATION): Flowable<T> {
    return this@throttleDefault
        .throttleFirst(duration, TimeUnit.SECONDS)
}


fun <T> Observable<T>.throttleDefault(duration: Long = THROTTLE_DURATION): Observable<T> {
    return this@throttleDefault
        .throttleFirst(duration, TimeUnit.SECONDS)
}

