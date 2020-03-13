package com.qifan.thepsetest.domain.model


data class OfferModel(
    val offers: List<OffersModelItem>
) {
    data class OffersModelItem(
        val sliceValue: Int?,
        val type: Type?,
        val value: Int
    )

    enum class Type(private val value: String) {
        PERCENTAGE("percentage"),
        MINUS("minus"),
        SLICE("slice"),
        UNKNOWN("");

        companion object {
            @JvmStatic
            fun fromValue(type: String?): Type =
                values().find { it.value.equals(type, true) }
                    ?: UNKNOWN
        }
    }
}