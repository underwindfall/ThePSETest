package com.qifan.thepsetest.app.book

import android.os.Bundle
import com.qifan.thepsetest.R
import com.qifan.thepsetest.app.base.activity.BaseActivity
import com.qifan.thepsetest.app.book.list.BookListFragment

class BookActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, BookListFragment())
                .commitNow()
        }
    }

    override fun getLayoutId(): Int = R.layout.main_activity

}
