package com.auro.scholr.core.common;

import java.util.HashMap;
import java.util.Map;

public enum PaytmError {
    U002("U002", "Amount is greater than approved prize"),
    U003("U003", "Amount is greater than max prize"),
    U004("U004", "Fund Already Redeemed for this user"),
    U005("U005", "Unable to get student details"),
    U006("U006", "Unable to save payment request"),

    A003("A003", "Amount is greater than max prize"),
    A004("A004", "Fund Already Redeemed for this user"),
    A005("A005", "Unable to get student details"),
    A002("A002", "Amount is greater than approved prize"),
    A006("A006", "Unable to save payment request"),

    W003("W003", "Amount is greater than max prize"),
    W004("W004", "Unable to get student details"),
    W005("W005", "Fund Already Redeemed for this user"),
    W002("W002", "Amount is greater than approved prize"),
    W006("W006", "Unable to save payment request"),

    DE_010("DE_010", "Parameter is mandatory and it's can't be null or blank."),
    DE_011("DE_011", "Parameter doesn't contain valid value."),
    DE_012("DE_012", "Amount must be a positive number with maximal 2 decimal places."),

    DE_013("DE_013", "Param doesn't contain valid length, It should be <length> or less."),
    DE_014("DE_014", "PPBL account no should be 12 digits and starts with 91."),
    DE_015("DE_015", "Purpose not valid."),
    DE_016("DE_016", "Month not valid."),
    DE_017("DE_017", "Year not valid."),
    DE_040("DE_040","Duplicate order id."),
    DE_041("DE_041","Unable to process your request. Please try after some time");


    private final String value;
    private final String text;

    private static HashMap<String, String> valueToTextMapping;

    private PaytmError(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public static String getStatus(String i) {
        if (valueToTextMapping == null) {
            initMapping();
        }
        return valueToTextMapping.get(i);
    }

    public static HashMap<String, String> initMapping() {
        valueToTextMapping = new HashMap<String, String>();
        for (PaytmError error : values()) {
            valueToTextMapping.put(error.value, error.text);
        }
        return valueToTextMapping;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PaytmError");
        sb.append("{value=").append(value);
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
