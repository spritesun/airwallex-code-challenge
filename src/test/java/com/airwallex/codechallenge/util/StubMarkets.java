package com.airwallex.codechallenge.util;

import com.airwallex.codechallenge.market.Markets;
import com.airwallex.codechallenge.market.Trend;

import java.util.OptionalDouble;

public class StubMarkets extends Markets {
    private OptionalDouble stubAverageRate;
    private Trend stubTrend;

    @Override
    public OptionalDouble getAverageRate(String currencyPair) {
        return stubAverageRate;
    }

    @Override
    public Trend getTrend(String currencyPair) {
        return stubTrend;
    }

    public void setStubAverageRate(double stubAverageRate) {
        this.stubAverageRate = OptionalDouble.of(stubAverageRate);
    }

    public void setStubTrend(Trend stubTrend) {
        this.stubTrend = stubTrend;
    }
}
