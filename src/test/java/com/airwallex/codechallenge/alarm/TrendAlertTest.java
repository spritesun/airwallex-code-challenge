package com.airwallex.codechallenge.alarm;

import com.airwallex.codechallenge.market.Trend;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TrendAlertTest {
    @Test
    void shouldConvertTrendAlertToJSONString() {
        Trend trend = new Trend();
        trend.update(1);
        TrendAlert alert = new TrendAlert(Instant.EPOCH, "CNYAUD", trend);

        assertEquals("{\"timestamp\":0.0,\"currencyPair\":\"CNYAUD\",\"alert\":\"rising\",\"second\":1}",
                alert.toJSONString());
    }
}