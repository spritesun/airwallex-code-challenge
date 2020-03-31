### Solution

The input of alerting service is a live stream of events.
Each events could have several consumers, here they are,
- Market
- Monitors

Market(aka. Quotation) maintain a slice of event source, the retention period is 5 minutes, 
which is enough to satisfy our monitor requirements.
Market data like rate average, trend are highly reusable information.

Every alert has their own monitor, which are,
- RateChangeMonitor
- TrendMonitor

This separation gives us flexibility to configure them.

Each monitor could has zero to many alarms, which could be,
- Standard Output alarm (in our requirement)
- Email alarm
- SMS alarm
- Slack alarm  

The way I hooked the alarms is very flexible. 
You can attach email alarm to one monitor, and attach slack alarm to another.    
   
### How to run
#### Build
```
./gradlew clean build
```
#### Unit Test
```
./gradlew test
```
#### Demo
```
./gradlew run --args example/input1.jsonl
./gradlew run --args example/input2.jsonl
```

### Assumption
- If the rate does not change for 1 second, the last of rising/falling trend will be ceased. 

### Things could improve
- More integration tests.
- More automation test cases to cover the edge cases.  
- More exception handling as defense guard.