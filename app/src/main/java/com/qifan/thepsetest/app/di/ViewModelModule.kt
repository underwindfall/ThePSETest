package com.qifan.thepsetest.app.di

import com.qifan.thepsetest.app.book.list.BookListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        BookListViewModel(get())
    }
}