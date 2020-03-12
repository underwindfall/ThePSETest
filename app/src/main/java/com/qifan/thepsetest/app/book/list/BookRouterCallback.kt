package com.qifan.thepsetest.app.book.list

import com.qifan.thepsetest.domain.model.BookModel

interface BookRouterCallback {
    fun navigateToOffer(books: MutableList<BookModel>)
}