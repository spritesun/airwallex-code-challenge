package com.airwallex.codechallenge.market;

import com.airwallex.codechallenge.message.RateMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;

/**
 * Quotation stores multiple markets for each currency pair.
 */

public class Markets {

    private Map<String, Market> markets;

    public Markets() {
        this.markets = new HashMap<>();
    }

    public void append(RateMessage rateMessage) throws UnsupportedRateMessageException {
        Market market = findOrCreateMarket(rateMessage.getCurrencyPair());
        market.append(rateMessage);
    }

    private Market findOrCreateMarket(String currencyPair) {
        Market market = markets.get(currencyPair);
        if (market == null) {
            market = new Market(currencyPair);
            markets.put(currencyPair, market);
        }
        return market;
    }

    public OptionalDouble getAverageRate(String currencyPair) {
        return findOrCreateMarket(currencyPair).getAverageRate();
    }

}
