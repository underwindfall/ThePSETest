package com.qifan.thepsetest.app.di

import com.qifan.thepsetest.domain.repository.GetBooksRepository
import com.qifan.thepsetest.domain.repository.GetBooksRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<GetBooksRepository> {
        GetBooksRepositoryImpl(get())
    }
}