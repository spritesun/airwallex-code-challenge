package com.airwallex.codechallenge.input

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

class Reader {

    private val mapper = jacksonObjectMapper()
        .registerModule(JavaTimeModule())

    fun read(filename: String): Stream<CurrencyConversionRate> =
        read(Files.lines(Paths.get(filename)))

    fun read(lines: Stream<String>): Stream<CurrencyConversionRate> =
            lines.map {
                mapper.readValue<CurrencyConversionRate>(it)
            }

}