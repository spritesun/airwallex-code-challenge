package com.airwallex.codechallenge.monitor;

import com.airwallex.codechallenge.alarm.TrendAlert;
import com.airwallex.codechallenge.market.Trend;
import com.airwallex.codechallenge.message.RateMessage;
import com.airwallex.codechallenge.util.StubAlarm;
import com.airwallex.codechallenge.util.StubMarkets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TrendMonitorTest {

    private StubMarkets markets = new StubMarkets();
    private StubAlarm alarm;

    @BeforeEach
    void beforeEach() {
        alarm = new StubAlarm();
    }
    @Test
    void shouldFireTrendAlertIfTrendLastMore15Minutes() {
        markets.setStubTrend(new Trend(Trend.State.RISING, 16 * 60));
        TrendMonitor monitor = new TrendMonitor(markets);
        monitor.registerAlarm(alarm);

        monitor.process(new RateMessage(Instant.now(), "CNYAUD", 1d));

        assertTrue(alarm.isLastAlertFiredWith(TrendAlert.class));
    }

    @Test
    void shouldNotFireTrendAlertIfTrendLastLess15Minutes() {
        markets.setStubTrend(new Trend(Trend.State.RISING, 14 * 60));
        TrendMonitor monitor = new TrendMonitor(markets);
        monitor.registerAlarm(alarm);

        monitor.process(new RateMessage(Instant.now(), "CNYAUD", 1d));

        assertFalse(alarm.isLastAlertFiredWith(TrendAlert.class));
    }

    @Test
    void shouldNotFireTrendAlertIfTrendNoChange() {
        markets.setStubTrend(new Trend(Trend.State.NO_CHANGE, 16 * 60));
        TrendMonitor monitor = new TrendMonitor(markets);
        monitor.registerAlarm(alarm);

        monitor.process(new RateMessage(Instant.now(), "CNYAUD", 1d));

        assertFalse(alarm.isLastAlertFiredWith(TrendAlert.class));
    }

    @Test
    void shouldOnlyFireOneTrendAlertPerMinute() {
        markets.setStubTrend(new Trend(Trend.State.RISING, 16 * 60));
        TrendMonitor monitor = new TrendMonitor(markets);
        monitor.registerAlarm(alarm);

        Instant now = Instant.now();
        monitor.process(new RateMessage(now, "CNYAUD", 1d));
        monitor.process(new RateMessage(now.plusSeconds(40), "CNYAUD", 1d));
        monitor.process(new RateMessage(now.plusSeconds(61), "CNYAUD", 1d));
        monitor.process(new RateMessage(now.plusSeconds(121), "CNYAUD", 1d));
        assertEquals(3, alarm.getFiredTimes());
    }
}