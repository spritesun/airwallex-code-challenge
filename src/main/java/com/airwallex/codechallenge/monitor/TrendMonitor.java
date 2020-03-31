package com.airwallex.codechallenge.monitor;

import com.airwallex.codechallenge.alarm.Alarm;
import com.airwallex.codechallenge.alarm.Alert;
import com.airwallex.codechallenge.alarm.TrendAlert;
import com.airwallex.codechallenge.market.Markets;
import com.airwallex.codechallenge.market.Trend;
import com.airwallex.codechallenge.message.RateMessage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TrendMonitor implements Monitor {

    private static final int TREND_THRESHOLD_IN_SECONDS = 15 * 60;
    public static final int SILENT_PERIOD_IN_SECONDS = 60;

    private final Markets markets;
    private List<Alarm> alarms = new ArrayList<>();
    private Instant lastAlertTime;

    public TrendMonitor(Markets markets) {
        this.markets = markets;
    }

    public void process(RateMessage message) {
        Trend trend = markets.getTrend(message.getCurrencyPair());
        if (trend == null) {
            return;
        }

        Instant currentTime = message.getTimestamp();
        if (trendLastLongEnough(trend) && noAlertSinceLastMinute(currentTime)) {
            alertAll(new TrendAlert(currentTime, message.getCurrencyPair(), trend), currentTime);
        }
    }

    private boolean noAlertSinceLastMinute(Instant currentTime) {
        if (null == lastAlertTime) {
            return true;
        }
        return lastAlertTime.plusSeconds(SILENT_PERIOD_IN_SECONDS - 1).isBefore(currentTime);
    }

    private boolean trendLastLongEnough(Trend trend) {
        return (trend.getState() != Trend.State.NO_CHANGE) && trend.getDuration() >= TREND_THRESHOLD_IN_SECONDS;
    }

    @Override
    public void registerAlarm(Alarm alarm) {
        alarms.add(alarm);
    }

    private void alertAll(Alert alert, Instant timestamp) {
        lastAlertTime = timestamp;
        for (Alarm alarm : alarms) {
            alarm.fireAlert(alert);
        }
    }
}
