package com.airwallex.codechallenge.input

import java.time.Instant

data class CurrencyConversionRate (
    val timestamp: Instant,
    val currencyPair: String,
    val rate: Double
)
