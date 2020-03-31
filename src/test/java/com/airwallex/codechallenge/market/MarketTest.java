package com.airwallex.codechallenge.market;

import com.airwallex.codechallenge.message.RateMessage;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MarketTest {

    public static final double DELTA = 1E-6d;
    private String currencyPair = "CNYAUD";
    private Market market = new Market(currencyPair);
    private Instant now = Instant.now();

    @Test
    void shouldUpdateAverageRateWithLatestRateEvent() throws UnsupportedRateMessageException {
        market.append(new RateMessage(now, currencyPair, 0.1d));
        assertEquals(OptionalDouble.of(0.1d), market.getAverageRate());

        market.append(new RateMessage(now.plusSeconds(1), currencyPair, 0.2d));
        assertEquals(0.15d, market.getAverageRate().getAsDouble(), DELTA);

        market.append(new RateMessage(now.plusSeconds(2), currencyPair, 0.3d));
        assertEquals(0.2d, market.getAverageRate().getAsDouble(), DELTA);
    }

    @Test
    void shouldIgnoreRate5MinsAgo() throws UnsupportedRateMessageException {
        market.append(new RateMessage(now.minusSeconds(6 * 60), currencyPair, 0.5d));

        market.append(new RateMessage(now, currencyPair, 0.1d));
        assertEquals(OptionalDouble.of(0.1d), market.getAverageRate());
    }

    @Test
    void shouldUpdateTrendWithLatestRateEvent() throws UnsupportedRateMessageException {
        market.append(new RateMessage(now, currencyPair, 0.3d));
        assertEquals(Trend.State.NO_CHANGE, market.getTrend().getState());
        assertEquals(1, market.getTrend().getDuration());
    }

    @Test
    void shouldUpdateToRisingTrendIfRateIncrease() throws UnsupportedRateMessageException {
        market.append(new RateMessage(now, currencyPair, 0.3d));
        market.append(new RateMessage(now.plusSeconds(1), currencyPair, 0.4d));
        assertEquals(Trend.State.RISING, market.getTrend().getState());
        assertEquals(1, market.getTrend().getDuration());
    }

    @Test
    void shouldUpdateTrendDurationIfTrendKeepRising() throws UnsupportedRateMessageException {
        market.append(new RateMessage(now, currencyPair, 0.3d));
        market.append(new RateMessage(now.plusSeconds(1), currencyPair, 0.4d));
        market.append(new RateMessage(now.plusSeconds(2), currencyPair, 0.5d));
        assertEquals(Trend.State.RISING, market.getTrend().getState());
        assertEquals(2, market.getTrend().getDuration());
    }

    @Test
    void shouldUpdateToFallingTrendIfRateDecrease() throws UnsupportedRateMessageException {
        market.append(new RateMessage(now, currencyPair, 0.3d));
        market.append(new RateMessage(now.plusSeconds(1), currencyPair, 0.2d));
        assertEquals(Trend.State.FALLING, market.getTrend().getState());
        assertEquals(1, market.getTrend().getDuration());
    }

    @Test
    void shouldUpdateTrendDurationIfTrendKeepFalling() throws UnsupportedRateMessageException {
        market.append(new RateMessage(now, currencyPair, 0.3d));
        market.append(new RateMessage(now.plusSeconds(1), currencyPair, 0.2d));
        market.append(new RateMessage(now.plusSeconds(2), currencyPair, 0.1d));
        assertEquals(Trend.State.FALLING, market.getTrend().getState());
        assertEquals(2, market.getTrend().getDuration());
    }

    @Test
    void shouldResetTrendDurationIfTrendChange() throws UnsupportedRateMessageException {
        market.append(new RateMessage(now, currencyPair, 0.3d));
        market.append(new RateMessage(now.plusSeconds(1), currencyPair, 0.2d));
        assertEquals(Trend.State.FALLING, market.getTrend().getState());
        market.append(new RateMessage(now.plusSeconds(2), currencyPair, 0.3d));
        assertEquals(Trend.State.RISING, market.getTrend().getState());
        assertEquals(1, market.getTrend().getDuration());
    }

    @Test
    void shouldResetIfTrendChangefromRisingToNoChange() throws UnsupportedRateMessageException {
        market.append(new RateMessage(now, currencyPair, 0.3d));
        market.append(new RateMessage(now.plusSeconds(1), currencyPair, 0.4d));
        assertEquals(Trend.State.RISING, market.getTrend().getState());
        market.append(new RateMessage(now.plusSeconds(2), currencyPair, 0.4d));
        assertEquals(Trend.State.NO_CHANGE, market.getTrend().getState());
    }
}