package com.qifan.thepsetest.app.book.list

import android.os.Bundle
import android.view.View
import com.qifan.thepsetest.R
import com.qifan.thepsetest.app.base.fragment.InjectionFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookListFragment : InjectionFragment() {
    private val listViewModel: BookListViewModel by viewModel()

    override fun getMenuId(): Int? = null
    override fun getLayoutId(): Int = R.layout.main_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewModel.getBooks()
    }

}
