package com.whattoeat.model.processor;

public class DataStandardization {
    private double[] values;
    private double[] standardizedValues;
    private int scale = 1;

    public DataStandardization(double[] values) {
        this.values = values;
    }

    public double[] getStandardizedValues() {
        this.standardizedValues = MinMaxNormalization.minMaxNormalize(this.values, this.scale);
        return this.standardizedValues;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
}