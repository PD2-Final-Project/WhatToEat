package com.whattoeat.model.processor;

public class MinMaxNormalization {
    public static double[] minMaxNormalize(double[] values, int scale) {
        double[] normalized = new double[values.length];
        double xmin = values[0];
        double xmax = values[0];
        for (int i = 1; i < values.length; i++) {
            if(values[i] > xmax) {
                xmax = values[i];
            }
        }
        for(int i = 1 ; i < values.length; i++) {
            if(values[i] < xmin) {
                xmin = values[i];
            }
        }
        for(int i = 0 ; i < values.length ; i ++) {
            normalized[i] = ((values[i] - xmin) / (xmax - xmin)) * scale;
        }
        return normalized;
    }
}
