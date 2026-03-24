package com.ccrm.model;

import java.time.LocalDate;

/**
 * Abstract base class representing a person in the campus system.
 * Demonstrates abstraction and inheritance principles.
 */
public abstract class Person {
    private String id;
    private String fullName;
    private String email;
    private LocalDate dateOfBirth;
    private String phoneNumber;

    public Person(String id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    // Abstract method that must be implemented by subclasses
    public abstract String getRole();

    // Getters and setters demonstrating encapsulation
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Email: %s, Role: %s", 
                           id, fullName, email, getRole());
    }
}
