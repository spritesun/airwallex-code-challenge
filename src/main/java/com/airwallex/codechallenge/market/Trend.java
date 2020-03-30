package com.airwallex.codechallenge.market;

public class Trend {
    public enum State {RISING, FALLING}

    private State state;
    private long duration;

    public Trend(State state, long duration) {
        this.state = state;
        this.duration = duration;
    }

    public State getState() {
        return state;
    }

    public long getDuration() {
        return duration;
    }
}
