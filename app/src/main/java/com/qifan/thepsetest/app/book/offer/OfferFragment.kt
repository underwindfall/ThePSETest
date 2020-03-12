package com.qifan.thepsetest.app.book.offer

import com.qifan.thepsetest.R
import com.qifan.thepsetest.app.base.ReactiveBehavior
import com.qifan.thepsetest.app.base.ReactiveBehaviorDelegate
import com.qifan.thepsetest.app.base.fragment.InjectionFragment


class OfferFragment :InjectionFragment(), ReactiveBehavior by ReactiveBehaviorDelegate() {
    override fun getMenuId(): Int? = null

    override fun getLayoutId(): Int = R.layout.offer_book_fragment

}