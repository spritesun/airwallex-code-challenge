package com.airwallex.codechallenge.alarm;

public class StdOutAlarm implements Alarm {
    @Override
    public void fireAlert(Alert alert) {
        System.out.println(alert.toJSONString());
    }
}
