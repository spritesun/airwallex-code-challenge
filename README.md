# Airwallex coding challenge

The aim of this exercise is to implement an "alerting" service which
will consume a file of currency conversion rates and
produce alerts for a number of situations.

This coding exercise can be performed in either Java or Kotlin. The build 
system is configured to handle both languages.

### Input

The format of the file will simulate a stream of currency
conversion rates. Each line will be properly structured
JSON (http://jsonlines.org/):

    { "timestamp": 1554933784.023, "currencyPair": "CNYAUD", "rate": 0.39281 }

The fields in the JSON record are:
- timestamp: the timestamp of the record in seconds since UNIX epoch, 
  with fractional seconds specified
- currencyPair: the sell and buy currencies which the rate relates to
- rate: the conversion rate

You may assume that for each currency pair, currency conversion rates are streamed
at a constant rate of one per second. ie. for two consecutive "CNYAUD" entries in
in the input file, they will have timestamps that differ by one second:

    { "timestamp": 1554933784.023, "currencyPair": "CNYAUD", "rate": 0.39281 }
    { "timestamp": 1554933784.087, "currencyPair": "USDAUD", "rate": 0.85641 }
    { "timestamp": 1554933785.023, "currencyPair": "CNYAUD", "rate": 0.39295 }

### Output

The alerting service should produce the following alerts, as JSON strings output to
standard output:
- when the spot rate for a currency pair changes by more than 10% from the 5 minute average for that currency pair
- when the spot rate has been rising/falling for 15 minutes. This alert should be
  throttled to only output once per minute and should report the length of time
  of the rise/fall in seconds.

The format of the alerts produced should be:

    { "timestamp": 1554933784.023, "currencyPair": "CNYAUD", "alert": "spotChange" }
    { "timestamp": 1554933784.023, "currencyPair": "CNYAUD", "alert": "falling", seconds: 3450 }

### Build and Execution

To build the application:
```bash
./gradlew clean build
```
To run the application:
```bash
./gradlew run --args example/input1.jsonl
```

### Submissions

Submissions will be assessed on:
- correctness
- code structure
- clean, readable code
- performance
