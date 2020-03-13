package com.qifan.thepsetest.app.book.offer

import com.qifan.thepsetest.domain.model.BookModel

interface OfferRouterCallback {
    fun getSelectedBooks(): MutableList<BookModel>
}