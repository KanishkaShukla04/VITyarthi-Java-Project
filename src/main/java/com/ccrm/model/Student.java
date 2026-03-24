package com.ccrm.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Student class extending Person, demonstrating inheritance.
 * Manages student-specific data and enrolled courses.
 */
public class Student extends Person {
    private String registrationNumber;
    private LocalDate enrollmentDate;
    private boolean isActive;
    private List<String> enrolledCourseIds;
    private double currentGPA;

    public Student(String id, String registrationNumber, String fullName, String email) {
        super(id, fullName, email);
        this.registrationNumber = registrationNumber;
        this.enrollmentDate = LocalDate.now();
        this.isActive = true;
        this.enrolledCourseIds = new ArrayList<>();
        this.currentGPA = 0.0;
    }

    @Override
    public String getRole() {
        return "Student";
    }

    public void enrollInCourse(String courseId) {
        if (!enrolledCourseIds.contains(courseId)) {
            enrolledCourseIds.add(courseId);
        }
    }

    public void unenrollFromCourse(String courseId) {
        enrolledCourseIds.remove(courseId);
    }

    public boolean isEnrolledInCourse(String courseId) {
        return enrolledCourseIds.contains(courseId);
    }

    // Getters and setters
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<String> getEnrolledCourseIds() {
        return new ArrayList<>(enrolledCourseIds);
    }

    public void setEnrolledCourseIds(List<String> enrolledCourseIds) {
        this.enrolledCourseIds = new ArrayList<>(enrolledCourseIds);
    }

    public double getCurrentGPA() {
        return currentGPA;
    }

    public void setCurrentGPA(double currentGPA) {
        this.currentGPA = currentGPA;
    }

    @Override
    public String toString() {
        return String.format("Student[ID: %s, RegNo: %s, Name: %s, Email: %s, Active: %s, GPA: %.2f]", 
                           getId(), registrationNumber, getFullName(), getEmail(), isActive, currentGPA);
    }
}
