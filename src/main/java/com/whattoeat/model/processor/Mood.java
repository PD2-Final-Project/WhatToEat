package com.whattoeat.model.processor;

public enum Mood {
    TERRIBLE(0),
    FRUSTRATION(1),
    BAD(2),
    CALM(3),
    NICE(4),
    GREAT(5);

    private int moodCode;
    private Mood(int moodCode) {
        this.moodCode = moodCode;
    }
    public int getMoodCode() { return this.moodCode; }
}
