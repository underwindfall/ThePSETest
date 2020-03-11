package com.qifan.thepsetest.app.book.list

import android.util.Log
import com.qifan.thepsetest.domain.repository.GetBooksRepository
import com.qifan.thepsetest.extension.reactive.mainThread
import com.qifan.thepsetest.app.base.viewmodel.BaseViewModel

class BookListViewModel(
    private val booksRepository: GetBooksRepository
) : BaseViewModel() {


    fun getBooks() {
        booksRepository.getBooks()
            .mainThread()
            .subscribe { it -> Log.d("QIfan", "$it") }
            .let(compositeDisposable::add)
    }
}
