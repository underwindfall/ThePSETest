package com.qifan.thepsetest.app.book.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.qifan.thepsetest.R
import com.qifan.thepsetest.app.base.ReactiveBehavior
import com.qifan.thepsetest.app.base.ReactiveBehaviorDelegate
import com.qifan.thepsetest.app.base.fragment.InjectionFragment
import com.qifan.thepsetest.domain.exception.PSEBookException
import com.qifan.thepsetest.domain.model.BookModel
import com.qifan.thepsetest.domain.model.Results
import com.qifan.thepsetest.extension.reactive.mainThread
import com.qifan.thepsetest.extension.reactive.subscribeAndLogError
import kotlinx.android.synthetic.main.book_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookListFragment : InjectionFragment(),
    ReactiveBehavior by ReactiveBehaviorDelegate() {
    private val bookListViewModel: BookListViewModel by viewModel()
    private lateinit var bookListAdapter: BookListAdapter

    override fun getMenuId(): Int? = null
    override fun getLayoutId(): Int = R.layout.book_list_fragment


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setUpRecyclerView()
        compositeDisposable.addAll(
            fetchBooks().subscribeAndLogError(),
            handleDataError().subscribeAndLogError(),
            handleDataLoading().subscribeAndLogError(),
            handleDataSuccess().subscribeAndLogError()
        )
    }

    private fun setUpToolbar() {
        activity?.apply {
            findViewById<Toolbar>(R.id.toolbar)?.apply {
                title = getString(R.string.toolbar_book_list)
            }
        }
    }

    private fun setUpRecyclerView() {
        bookListAdapter = BookListAdapter()
        books_recycler_view.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = bookListAdapter
        }
    }

    private fun handleDataLoading() = bookListViewModel.data
        .loading
        .mainThread()
        .doOnNext(::displayLoading)


    private fun handleDataSuccess() = bookListViewModel.data
        .success
        .mainThread()
        .doOnNext { result ->
            when (result) {
                is Results.Success -> displayBooks(result.data)
                is Results.Failure -> displayError(result.error)
            }
        }

    private fun handleDataError() = bookListViewModel.data
        .hasError
        .mainThread()
        .doOnNext { (hasError, error) ->
            if (hasError) {
                displayError(error)
            }
        }

    private fun fetchBooks() = bookListViewModel.getBooks()
        .mainThread()

    private fun displayBooks(data: List<BookModel>) {
        books_recycler_view.visibility = View.VISIBLE
        loading.visibility = View.GONE
        error_message.visibility = View.GONE
        bookListAdapter.setData(data)
    }

    private fun displayError(errorMessage: Throwable?) {
        books_recycler_view.visibility = View.GONE
        loading.visibility = View.GONE
        error_message.visibility = View.VISIBLE
        when (errorMessage) {
            is PSEBookException.NetworkException -> error_message.text =
                getString(R.string.error_network)
            else -> error_message.text = getString(R.string.error_message)
        }
    }

    private fun displayLoading(show: Boolean) {
        if (show) {
            books_recycler_view.visibility = View.VISIBLE
            loading.visibility = View.GONE
            error_message.visibility = View.GONE
        } else {
            books_recycler_view.visibility = View.GONE
            loading.visibility = View.VISIBLE
            error_message.visibility = View.GONE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}
