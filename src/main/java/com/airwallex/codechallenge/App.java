package com.airwallex.codechallenge;

import com.airwallex.codechallenge.input.Reader;

public class App {

    public static void main(String[] args) {
        Reader reader = new Reader();

        reader
                .read(args[0])
                //
                // TODO process the input
                //
                .forEach(currencyConversionRate -> System.out.println(currencyConversionRate.toString()));
    }

}
