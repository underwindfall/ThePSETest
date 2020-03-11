package com.qifan.thepsetest.domain.exception

sealed class PSEBookException(message: String? = null, throwable: Throwable? = null) :
    Exception(message, throwable) {
    class GeneralException(throwable: Throwable?) : PSEBookException(throwable = throwable)
    class EmptyException(message: String? = "Empty Restaurant Found") : PSEBookException(message)
    class NetworkException(message: String? = null, throwable: Throwable? = null) :
        PSEBookException(message, throwable)
}