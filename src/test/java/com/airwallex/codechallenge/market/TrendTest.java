package com.airwallex.codechallenge.market;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrendTest {

    @Test
    void shouldUpdateTrendAndIncreaseDurationIfTrendSame() {
        Trend trend = new Trend();
        trend.update(0);
        assertEquals(0, trend.getState().getValue());
        assertEquals(2, trend.getDuration());
    }

    @Test
    void shouldResetTrendIfTrendChange() {
        Trend trend = new Trend();
        trend.update(-1);
        assertEquals(-1, trend.getState().getValue());
        assertEquals(1, trend.getDuration());
    }

    @Test
    void shouldThrowExceptionIfUpdateWithInvalid() {
        Trend trend = new Trend();

        assertThrows(IllegalArgumentException.class, () -> trend.update(2));
    }
}