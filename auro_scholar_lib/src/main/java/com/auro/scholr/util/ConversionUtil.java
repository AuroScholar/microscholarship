package com.auro.scholr.util;

public enum ConversionUtil {
    INSTANCE;

    public int convertStringToInteger(String value) {
        int val = 0;
        try {
            if (value != null && !value.isEmpty()) {
                val = Integer.parseInt(value);
                return val;
            } else {
                return val;
            }
        } catch (Exception e) {
            return val;
        }
    }

    public double convertStringToDouble(String value) {
        double val = 0;
        try {
            if (value != null && !value.isEmpty()) {
                val = Double.parseDouble(value);
                return val;
            } else {
                return val;
            }
        } catch (Exception e) {
            return val;
        }
    }


}
