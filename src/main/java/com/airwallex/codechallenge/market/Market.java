package com.airwallex.codechallenge.market;

import com.airwallex.codechallenge.message.RateMessage;

import java.time.Instant;
import java.util.LinkedList;
import java.util.Objects;
import java.util.OptionalDouble;

/**
 * A market hold information like market average rate, market trend for each currency pair
 */

public class Market {
    public static final int TIME_WINDOW_IN_SECONDS = 5 * 60;
    private final String currencyPair;
    private OptionalDouble averageRate;
    private LinkedList<RateMessage> messages;
    private Instant currentTime;

    public Market(String currencyPair) {
        this.currencyPair = currencyPair;
        this.messages = new LinkedList<>();
    }

    public OptionalDouble getAverageRate() {
        return averageRate;
    }

    public void append(RateMessage rateMessage) throws UnsupportedRateMessageException {
        if (!Objects.equals(currencyPair, rateMessage.getCurrencyPair())) {
            throw new UnsupportedRateMessageException();
        }
        currentTime = rateMessage.getTimestamp();
        removeExpiredMessage();
        messages.add(rateMessage);
        averageRate = messages.stream().mapToDouble(RateMessage::getRate).average();
    }

    private void removeExpiredMessage() {
        if (messages.isEmpty()) {
            return;
        }
        RateMessage firstMessage = messages.getFirst();
        if (messageExpired(firstMessage)) {
            messages.removeFirst();
        }
    }

    private boolean messageExpired(RateMessage message) {
        return message.getTimestamp().plusSeconds(TIME_WINDOW_IN_SECONDS).isBefore(currentTime);
    }
}
