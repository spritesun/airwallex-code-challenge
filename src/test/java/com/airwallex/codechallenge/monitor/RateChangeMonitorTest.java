package com.airwallex.codechallenge.monitor;

import com.airwallex.codechallenge.alarm.ChangeAlert;
import com.airwallex.codechallenge.message.RateMessage;
import com.airwallex.codechallenge.util.StubAlarm;
import com.airwallex.codechallenge.util.StubMarkets;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RateChangeMonitorTest {

    private StubMarkets markets = new StubMarkets();
    private StubAlarm alarm = new StubAlarm();

    @Test
    void shouldFireChangeAlertIfRateChangeMoreThan10Percent() {
        markets.setStubAverageRate(1d);
        RateChangeMonitor monitor = new RateChangeMonitor(markets);
        monitor.registerAlarm(alarm);

        monitor.process(new RateMessage(Instant.now(), "CNYAUD", 1.2d));

        assertTrue(alarm.isLastAlertFiredWith(ChangeAlert.class));
    }

    @Test
    void shouldNotFireChangeAlertIfRateChangeLessThan10Percent() {
        markets.setStubAverageRate(1d);
        RateChangeMonitor monitor = new RateChangeMonitor(markets);
        monitor.registerAlarm(alarm);

        monitor.process(new RateMessage(Instant.now(), "CNYAUD", 1.05d));

        assertFalse(alarm.isLastAlertFiredWith(ChangeAlert.class));
    }
}