package com.airwallex.codechallenge;

import com.airwallex.codechallenge.alarm.StdOutAlarm;
import com.airwallex.codechallenge.market.Markets;
import com.airwallex.codechallenge.market.UnsupportedRateMessageException;
import com.airwallex.codechallenge.message.RateMessage;
import com.airwallex.codechallenge.monitor.RateChangeMonitor;
import com.airwallex.codechallenge.reader.Reader;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        StdOutAlarm stdOutAlarm = new StdOutAlarm();

        Markets markets = new Markets();

        RateChangeMonitor rateChangeMonitor = new RateChangeMonitor(markets);
        rateChangeMonitor.registerAlarm(stdOutAlarm);

        Reader reader = new Reader();

        reader.read(args[0]).forEach(rateMessage -> {
            updateMarket(markets, rateMessage);
            rateChangeMonitor.process(rateMessage);
        });
    }

    private static void updateMarket(Markets markets, RateMessage rateMessage) {
        try {
            markets.append(rateMessage);
        } catch (UnsupportedRateMessageException e) {
            e.printStackTrace();
        }
    }

}
