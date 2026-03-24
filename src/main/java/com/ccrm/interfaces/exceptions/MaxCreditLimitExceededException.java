package com.ccrm.exceptions;

/**
 * Custom checked exception for when a student exceeds the maximum credit limit.
 * Demonstrates custom exception handling.
 */
public class MaxCreditLimitExceededException extends Exception {
    private final int attemptedCredits;
    private final int maxAllowedCredits;

    public MaxCreditLimitExceededException(int attemptedCredits, int maxAllowedCredits) {
        super(String.format("Credit limit exceeded: Attempted %d credits, but maximum allowed is %d", 
                          attemptedCredits, maxAllowedCredits));
        this.attemptedCredits = attemptedCredits;
        this.maxAllowedCredits = maxAllowedCredits;
    }

    public int getAttemptedCredits() {
        return attemptedCredits;
    }

    public int getMaxAllowedCredits() {
        return maxAllowedCredits;
    }
}
