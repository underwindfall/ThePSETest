package com.qifan.thepsetest.app.book

import android.os.Bundle
import com.qifan.thepsetest.R
import com.qifan.thepsetest.app.base.activity.BaseActivity
import com.qifan.thepsetest.app.book.list.BookListFragment
import com.qifan.thepsetest.app.book.list.BookRouterCallback
import com.qifan.thepsetest.app.book.offer.OfferFragment
import com.qifan.thepsetest.domain.model.BookModel

class BookActivity : BaseActivity(), BookRouterCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, BookListFragment())
                .commitNow()
        }
    }

    override fun getLayoutId(): Int = R.layout.main_activity

    override fun navigateToOffer(books: MutableList<BookModel>) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, OfferFragment())
            .addToBackStack("Offer")
            .commit()
    }

}
