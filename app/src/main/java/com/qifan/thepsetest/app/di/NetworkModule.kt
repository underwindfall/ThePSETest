package com.qifan.thepsetest.app.di

import com.qifan.thepsetest.data.api.BookApi
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {

    single<Retrofit>(createdAtStart = true) {
        Retrofit.Builder()
            .baseUrl(BookApi.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    single(createdAtStart = true) {
        get<Retrofit>().create(BookApi::class.java)
    }
}