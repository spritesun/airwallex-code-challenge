package com.airwallex.codechallenge.monitor;

import com.airwallex.codechallenge.message.RateMessage;
import com.airwallex.codechallenge.alarm.Alarm;
import com.airwallex.codechallenge.alarm.Alert;
import com.airwallex.codechallenge.alarm.ChangeAlert;
import com.airwallex.codechallenge.market.Markets;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class RateChangeMonitor implements Monitor {
    private static final double CHANGE_THRESHOLD = 0.1d;

    private final Markets markets;
    private List<Alarm> alarms;

    public RateChangeMonitor(Markets markets) {
        this.markets = markets;
        this.alarms = new ArrayList<>();
    }

    public void process(RateMessage message) {
        if (changeOverThreshold(message)) {
            alertAll(new ChangeAlert(message.getTimestamp(), message.getCurrencyPair()));
        }
    }

    private void alertAll(Alert alert) {
        for (Alarm alarm : alarms) {
            alarm.fireAlert(alert);
        }
    }

    private boolean changeOverThreshold(RateMessage message) {
        OptionalDouble averageRate = markets.getAverageRate(message.getCurrencyPair());
        if (!averageRate.isPresent()) {
            return false;
        }
        double avg = averageRate.getAsDouble();
        return (Math.abs(avg - message.getRate()) / avg) > CHANGE_THRESHOLD;
    }

    @Override
    public void registerAlarm(Alarm alarm) {
        alarms.add(alarm);
    }
}
