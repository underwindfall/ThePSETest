package com.qifan.thepsetest.domain.repository

import com.qifan.thepsetest.data.api.BookApi
import com.qifan.thepsetest.data.mapper.toModel
import com.qifan.thepsetest.domain.model.BookModel
import com.qifan.thepsetest.extension.reactive.io
import io.reactivex.Single

interface GetBooksRepository {
    fun getBooks(): Single<List<BookModel>>
}


class GetBooksRepositoryImpl(private val api: BookApi) : GetBooksRepository {
    override fun getBooks(): Single<List<BookModel>> {
        return api.getBooks()
            .map { books -> books.map { it.toModel() } }
            .io()
    }
}