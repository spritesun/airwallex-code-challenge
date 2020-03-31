package com.airwallex.codechallenge.market;

import com.airwallex.codechallenge.message.RateMessage;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class MarketsTest {
    private String currencyPair = "CNYAUD";
    private Instant now = Instant.now();

    @Test
    void shouldGetAverageRateForGivenCurrencyPair() throws UnsupportedRateMessageException {
        Markets markets = new Markets();
        markets.append(new RateMessage(now, currencyPair, 0.1d));

        assertEquals(0.1d, markets.getAverageRate("CNYAUD").getAsDouble());
    }

    @Test
    void shouldNotGetAverageRateIfCurrencyPairNotExist() throws UnsupportedRateMessageException {
        Markets markets = new Markets();
        markets.append(new RateMessage(now, currencyPair, 0.1d));

        assertNull(markets.getAverageRate("USDAUD"));
    }

    @Test
    void shouldGetTrendForGivenCurrencyPair() throws UnsupportedRateMessageException {
        Markets markets = new Markets();
        markets.append(new RateMessage(now, currencyPair, 0.1d));

        assertEquals(Trend.State.NO_CHANGE, markets.getTrend(currencyPair).getState());
    }

    @Test
    void shouldGetDefaultTrendIfCurrencyPairNotExist() throws UnsupportedRateMessageException {
        Markets markets = new Markets();
        markets.append(new RateMessage(now, currencyPair, 0.1d));

        assertEquals(Trend.State.NO_CHANGE, markets.getTrend(currencyPair).getState());
    }
}