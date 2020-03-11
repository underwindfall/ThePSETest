package com.qifan.thepsetest.domain

import com.qifan.thepsetest.domain.exception.PSEBookException
import com.qifan.thepsetest.domain.model.Results
import retrofit2.Response
import java.io.IOException

fun <T> processApiResponse(response: Response<T>): Results<T> {
    return try {
        val responseCode = response.code()
        val responseMessage = response.message()
        if (response.isSuccessful) {
            Results.success(response.body()!!)
        } else {
            val errorMessage =
                "responseCode ======> $responseCode responseMessage =====>$responseMessage "
            Results.failure(PSEBookException.NetworkException(errorMessage))
        }
    } catch (e: IOException) {
        Results.failure(PSEBookException.NetworkException())
    }
}
