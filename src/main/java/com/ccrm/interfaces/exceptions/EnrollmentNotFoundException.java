package com.ccrm.exceptions;

/**
 * Custom unchecked exception for when an enrollment is not found.
 * Demonstrates custom exception handling.
 */
public class EnrollmentNotFoundException extends RuntimeException {
    private final String enrollmentId;

    public EnrollmentNotFoundException(String enrollmentId) {
        super(String.format("Enrollment with ID '%s' not found", enrollmentId));
        this.enrollmentId = enrollmentId;
    }

    public String getEnrollmentId() {
        return enrollmentId;
    }
}
