package com.qifan.thepsetest.data.api

import com.qifan.thepsetest.data.entity.BookEntity
import com.qifan.thepsetest.data.entity.OfferEntity
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApi {
    @GET("/books")
    fun getBooks(): Single<Response<List<BookEntity>>>

    @GET("/books/{isbn}/commercialOffers")
    fun getReductionOffers(@Path("isbn") isbn: String): Single<Response<OfferEntity>>

    companion object {
        const val HOST = "henri-potier.xebia.fr/"
        const val ENDPOINT = "http://$HOST"
    }
}