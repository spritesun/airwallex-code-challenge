package com.airwallex.codechallenge.alarm;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ChangeAlertTest {

    @Test
    void shouldConvertChangeAlertToJSONString() {
        ChangeAlert alert = new ChangeAlert(Instant.EPOCH, "CNYAUD");
        assertEquals("{\"timestamp\":0.0,\"currencyPair\":\"CNYAUD\",\"alert\":\"spotChange\"}",
                alert.toJSONString());
    }
}