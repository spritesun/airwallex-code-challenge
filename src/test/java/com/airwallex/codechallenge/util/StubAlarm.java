package com.airwallex.codechallenge.util;

import com.airwallex.codechallenge.alarm.Alarm;
import com.airwallex.codechallenge.alarm.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StubAlarm implements Alarm {
    private List<Alert> alerts = new ArrayList<>();

    @Override
    public void fireAlert(Alert alert) {
        this.alerts.add(alert);
    }

    public boolean isLastAlertFiredWith(Object alertClass) {
        if (alerts.size() < 1 || alerts.get(alerts.size() - 1) == null) {
            return false;
        }
        return Objects.equals(alerts.get(alerts.size() - 1).getClass(), alertClass);
    }

    public int getFiredTimes() {
        return alerts.size();
    }
}
