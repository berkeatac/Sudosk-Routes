package com.berkeatac.sudoskroutes.Model;

import com.berkeatac.sudoskroutes.R;

public enum Grade {
    GRADE03C("0 - 3C+", 0, R.color.colorGrade0),
    GRADE4A4C("4A - 4C+", 1, R.color.colorGrade1),
    GRADE5A5B("5A - 5B+", 2, R.color.colorGrade2),
    GRADE5C6A("5C - 6A+", 3, R.color.colorGrade3),
    GRADE6B6C("6B - 6C+", 4, R.color.colorGrade4),
    GRADE7A7B("7A - 7B", 5, R.color.colorGrade5),
    GRADE7B("7B+", 6, R.color.colorGrade6);

    private String stringValue;
    private int intValue;
    private int colorValue;

    Grade(String toString, int value, int color) {
        stringValue = toString;
        intValue = value;
        colorValue = color;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public int getColorId() { return colorValue;}
}
