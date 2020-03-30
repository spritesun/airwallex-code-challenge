package com.airwallex.codechallenge.alarm;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.Instant;

import static com.fasterxml.jackson.module.kotlin.ExtensionsKt.jacksonObjectMapper;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ChangeAlert implements Alert {
    private static final String SPOT_CHANGE = "spotChange";

    private Instant timestamp;
    private String currencyPair;
    private String alert = SPOT_CHANGE;

    public ChangeAlert(Instant timestamp, String currencyPair) {
        this.timestamp = timestamp;
        this.currencyPair = currencyPair;
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
