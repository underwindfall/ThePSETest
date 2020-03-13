package com.qifan.thepsetest.app.book.offer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.qifan.thepsetest.R
import com.qifan.thepsetest.app.base.ReactiveBehavior
import com.qifan.thepsetest.app.base.ReactiveBehaviorDelegate
import com.qifan.thepsetest.app.base.fragment.InjectionFragment
import com.qifan.thepsetest.domain.exception.PSEBookException
import com.qifan.thepsetest.domain.model.Results
import com.qifan.thepsetest.extension.reactive.mainThread
import com.qifan.thepsetest.extension.reactive.subscribeAndLogError
import kotlinx.android.synthetic.main.offer_book_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ERROR = "Activity should implement OfferRouterCallback interface"

class OfferFragment : InjectionFragment(),
    ReactiveBehavior by ReactiveBehaviorDelegate() {

    private val offerViewModel: OfferViewModel by viewModel()
    private lateinit var offerListAdapter: OfferListAdapter
    private val routerCallback by lazy<OfferRouterCallback> {
        activity.let {
            check(it is OfferRouterCallback) { ERROR }
            it
        }
    }

    override fun getLayoutId(): Int = R.layout.offer_book_fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        routerCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setUpRecyclerView()
        compositeDisposable.addAll(
            fetchOfferData().subscribeAndLogError(),
            handOfferLoading().subscribeAndLogError(),
            handleOfferError().subscribeAndLogError(),
            handleOfferSuccess().subscribeAndLogError()
        )
    }

    private fun fetchOfferData() = offerViewModel.getReduction(routerCallback.getSelectedBooks())
        .mainThread()

    private fun handOfferLoading() = offerViewModel.data
        .loading
        .mainThread()
        .doOnNext(::displayLoading)

    private fun handleOfferError() = offerViewModel.data
        .hasError
        .mainThread()
        .doOnNext { (hasError, error) ->
            if (hasError) {
                displayError(error)
            } else {
                displayLoading(true)
            }
        }

    private fun handleOfferSuccess() = offerViewModel.data
        .success
        .mainThread()
        .doOnNext { result ->
            when (result) {
                is Results.Success -> bindData(result.data)
                is Results.Failure -> displayError(result.error)
            }
        }

    private fun displayError(error: Throwable?) {
        loading.visibility = View.GONE
        rv_selected_books.visibility = View.GONE
        offer_container.visibility = View.GONE
        error_text.visibility = View.VISIBLE
        when (error) {
            is PSEBookException.NetworkException -> error_text.text =
                getString(R.string.error_network)
            else -> error_text.text = getString(R.string.error_message)
        }
    }

    private fun displayLoading(show: Boolean) {
        if (show) {
            loading.visibility = View.VISIBLE
            rv_selected_books.visibility = View.GONE
            offer_container.visibility = View.GONE
            error_text.visibility = View.GONE
        } else {
            loading.visibility = View.GONE
            rv_selected_books.visibility = View.VISIBLE
            offer_container.visibility = View.VISIBLE
            error_text.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(data: ReductionModel) {
        loading.visibility = View.GONE
        rv_selected_books.visibility = View.VISIBLE
        offer_container.visibility = View.VISIBLE
        error_text.visibility = View.GONE
        offerListAdapter.setData(routerCallback.getSelectedBooks())
        total.text = data.totalPrice.toString() + "€"
        reduction.text = data.reduction
        result_offer.text = data.result
    }


    private fun setUpToolbar() {
        activity?.apply {
            findViewById<Toolbar>(R.id.toolbar)?.apply {
                title = getString(R.string.toolbar_book_offer)
                navigationIcon = getDrawable(R.drawable.ic_arrow_back_black_24dp)
                setNavigationOnClickListener { goBack() }
            }
        }
    }

    private fun setUpRecyclerView() {
        rv_selected_books.apply {
            offerListAdapter = OfferListAdapter()
            adapter = offerListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    private fun goBack() {
        requireActivity().onBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}