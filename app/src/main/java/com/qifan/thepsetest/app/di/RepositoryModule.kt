package com.qifan.thepsetest.app.di

import com.qifan.thepsetest.data.GetBooksRepositoryImpl
import com.qifan.thepsetest.domain.repository.GetBooksRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<GetBooksRepository> {
        GetBooksRepositoryImpl(get())
    }
}