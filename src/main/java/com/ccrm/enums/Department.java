package com.ccrm.enums;

/**
 * Enum representing academic departments.
 * Demonstrates enum usage for categorization.
 */
public enum Department {
    COMPUTER_SCIENCE("Computer Science", "CS"),
    MATHEMATICS("Mathematics", "MATH"),
    PHYSICS("Physics", "PHYS"),
    CHEMISTRY("Chemistry", "CHEM"),
    BIOLOGY("Biology", "BIO"),
    ENGLISH("English", "ENG"),
    HISTORY("History", "HIST"),
    BUSINESS("Business", "BUS"),
    ENGINEERING("Engineering", "ENG"),
    PSYCHOLOGY("Psychology", "PSYC");

    private final String fullName;
    private final String abbreviation;

    Department(String fullName, String abbreviation) {
        this.fullName = fullName;
        this.abbreviation = abbreviation;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
