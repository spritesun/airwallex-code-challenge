package com.airwallex.codechallenge.reader;

import com.airwallex.codechallenge.message.RateMessage;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {

    @Test
    void read() {
        Reader reader = new Reader();
        reader.read(Arrays.asList(
                "{ \"timestamp\": 1554933784.023, \"currencyPair\": \"CNYAUD\", \"rate\": 0.39281 }",
                "{ \"timestamp\": 1554933785.023, \"currencyPair\": \"CNYAUD\", \"rate\": 0.39281 }"
        ).stream()).forEach(message -> {
            assertEquals(RateMessage.class, message.getClass());
        });
    }
}