package com.airwallex.codechallenge.market;

public class Trend {
    private static final long DEFAULT_DURATION = 1L;
    private State state = State.NO_CHANGE;
    private long duration = DEFAULT_DURATION;


    public Trend(State state, int duration) {
        this.state = state;
        this.duration = duration;
    }

    public Trend() {
    }

    public State getState() {
        return state;
    }

    public long getDuration() {
        return duration;
    }

    // Assume rateMessage will post every 1 second
    public void update(int newTrend) {
        if (state.getValue() == newTrend) {
            duration++;
        } else {
            state = State.forInt(newTrend);
            duration = DEFAULT_DURATION;
        }
    }

    public enum State {
        FALLING(-1),
        NO_CHANGE(0),
        RISING(1);

        private final int value;

        State(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static State forInt(int val) {
            if (val > 1 || val < -1) {
                throw new IllegalArgumentException("Invalid trend state val" + val);
            }
            return values()[val + 1];
        }
    }
}
