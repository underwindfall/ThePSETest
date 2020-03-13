package com.qifan.thepsetest.app.book.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.qifan.thepsetest.R
import com.qifan.thepsetest.app.base.ReactiveBehavior
import com.qifan.thepsetest.app.base.ReactiveBehaviorDelegate
import com.qifan.thepsetest.app.base.fragment.InjectionFragment
import com.qifan.thepsetest.domain.exception.PSEBookException
import com.qifan.thepsetest.domain.model.BookModel
import com.qifan.thepsetest.domain.model.Results
import com.qifan.thepsetest.extension.reactive.*
import kotlinx.android.synthetic.main.book_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ERROR = "Activity should implement BookRouterCallback interface"

class BookListFragment : InjectionFragment(),
    ReactiveBehavior by ReactiveBehaviorDelegate() {
    private var selectedBooks = mutableListOf<BookModel>()
    private val bookListViewModel: BookListViewModel by viewModel()
    private lateinit var bookListAdapter: BookListAdapter
    private val routerCallback by lazy<BookRouterCallback> {
        activity.let {
            check(it is BookRouterCallback) { ERROR }
            it
        }
    }

    override fun getLayoutId(): Int = R.layout.book_list_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        routerCallback
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setUpRecyclerView()
        compositeDisposable.addAll(
            fetchBooks().subscribeAndLogError(),
            handleDataLoading().subscribeAndLogError(),
            handleDataError().subscribeAndLogError(),
            handleDataSuccess().subscribeAndLogError(),
            handleFabClick().subscribeAndLogError(),
            handleSelectedBook().subscribeAndLogError()
        )
    }

    private fun setUpToolbar() {
        activity?.apply {
            findViewById<Toolbar>(R.id.toolbar)?.apply {
                title = getString(R.string.toolbar_book_list)
                navigationIcon = null
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
            } else {
                displayLoading(true)
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
            books_recycler_view.visibility = View.GONE
            loading.visibility = View.VISIBLE
            error_message.visibility = View.GONE
        } else {
            books_recycler_view.visibility = View.VISIBLE
            loading.visibility = View.GONE
            error_message.visibility = View.GONE
        }
    }


    private fun handleFabClick() = fab_offer.clicks()
        .toFlowableDefault()
        .throttleDefault()
        .doOnNext {
            if (selectedBooks.isNotEmpty()) {
                routerCallback.navigateToOffer(selectedBooks)
            } else {
                Snackbar.make(fab_offer, R.string.need_add_shop, Snackbar.LENGTH_SHORT).show()
            }
        }


    private fun handleSelectedBook() = bookListAdapter.selectedList
        .doOnNext {
            selectedBooks.clear()
            selectedBooks = it.toMutableList()
        }

    override fun onDestroyView() {
        super.onDestroyView()
        selectedBooks.clear()
        compositeDisposable.clear()
    }
}
