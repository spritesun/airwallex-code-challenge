package com.airwallex.codechallenge.alarm;

import com.airwallex.codechallenge.market.Trend;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.Instant;

import static com.fasterxml.jackson.module.kotlin.ExtensionsKt.jacksonObjectMapper;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TrendAlert implements Alert {

    private final Instant timestamp;
    private final String currencyPair;
    private final String alert;
    private final long second;

    public TrendAlert(Instant timestamp, String currencyPair, Trend trend) {
        this.timestamp = timestamp;
        this.currencyPair = currencyPair;
        this.alert = (trend.getState() == Trend.State.RISING ? "rising" : "falling");
        this.second = trend.getDuration();
    }

    @Override
    public String toJSONString() {
        ObjectMapper mapper = jacksonObjectMapper().registerModule(new JavaTimeModule());
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
