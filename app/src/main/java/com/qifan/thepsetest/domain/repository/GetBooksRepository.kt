package com.qifan.thepsetest.domain.repository

import com.qifan.thepsetest.domain.model.BookModel
import com.qifan.thepsetest.domain.model.Results
import io.reactivex.Single

interface GetBooksRepository {
    fun getBooks(): Single<Results<List<BookModel>>>
}
