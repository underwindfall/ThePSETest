package com.qifan.thepsetest.data

import com.qifan.thepsetest.data.api.BookApi
import com.qifan.thepsetest.data.transformer.bookResponseToModelResult
import com.qifan.thepsetest.domain.model.BookModel
import com.qifan.thepsetest.domain.model.Results
import com.qifan.thepsetest.domain.repository.GetBooksRepository
import com.qifan.thepsetest.extension.reactive.io
import io.reactivex.Single

class GetBooksRepositoryImpl(private val api: BookApi) : GetBooksRepository {
    override fun getBooks(): Single<Results<List<BookModel>>> {
        return api.getBooks()
            .compose(bookResponseToModelResult())
            .io()
    }
}