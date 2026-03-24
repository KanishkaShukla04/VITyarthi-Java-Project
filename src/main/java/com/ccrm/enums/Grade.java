package com.ccrm.enums;

/**
 * Enum representing letter grades with associated grade points.
 * Demonstrates enum usage with constructors, fields, and methods.
 */
public enum Grade {
    A_PLUS("A+", 4.0),
    A("A", 4.0),
    A_MINUS("A-", 3.7),
    B_PLUS("B+", 3.3),
    B("B", 3.0),
    B_MINUS("B-", 2.7),
    C_PLUS("C+", 2.3),
    C("C", 2.0),
    C_MINUS("C-", 1.7),
    D_PLUS("D+", 1.3),
    D("D", 1.0),
    F("F", 0.0);

    private final String letterGrade;
    private final double gradePoints;

    Grade(String letterGrade, double gradePoints) {
        this.letterGrade = letterGrade;
        this.gradePoints = gradePoints;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public double getGradePoints() {
        return gradePoints;
    }

    /**
     * Converts a numeric score to a letter grade.
     * @param score The numeric score (0-100)
     * @return The corresponding Grade enum
     */
    public static Grade fromScore(double score) {
        if (score >= 97) return A_PLUS;
        if (score >= 93) return A;
        if (score >= 90) return A_MINUS;
        if (score >= 87) return B_PLUS;
        if (score >= 83) return B;
        if (score >= 80) return B_MINUS;
        if (score >= 77) return C_PLUS;
        if (score >= 73) return C;
        if (score >= 70) return C_MINUS;
        if (score >= 67) return D_PLUS;
        if (score >= 60) return D;
        return F;
    }

    @Override
    public String toString() {
        return letterGrade;
    }
}
