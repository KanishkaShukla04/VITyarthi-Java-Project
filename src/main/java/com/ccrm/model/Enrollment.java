package com.ccrm.model;

import com.ccrm.enums.Grade;
import java.time.LocalDate;

/**
 * Enrollment class representing student enrollment in courses with grades.
 * Demonstrates association between Student and Course entities.
 */
public class Enrollment {
    private String enrollmentId;
    private String studentId;
    private String courseId;
    private LocalDate enrollmentDate;
    private LocalDate completionDate;
    private double numericGrade;
    private Grade letterGrade;
    private boolean isCompleted;
    private boolean isActive;

    public Enrollment(String enrollmentId, String studentId, String courseId) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = LocalDate.now();
        this.isCompleted = false;
        this.isActive = true;
        this.numericGrade = 0.0;
        this.letterGrade = null;
    }

    public void recordGrade(double numericGrade) {
        this.numericGrade = numericGrade;
        this.letterGrade = Grade.fromScore(numericGrade);
        this.isCompleted = true;
        this.completionDate = LocalDate.now();
    }

    public void withdraw() {
        this.isActive = false;
        this.completionDate = LocalDate.now();
    }

    // Getters and setters
    public String getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(String enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public double getNumericGrade() {
        return numericGrade;
    }

    public void setNumericGrade(double numericGrade) {
        this.numericGrade = numericGrade;
    }

    public Grade getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(Grade letterGrade) {
        this.letterGrade = letterGrade;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return String.format("Enrollment[ID: %s, Student: %s, Course: %s, Grade: %s (%.1f), Completed: %s]", 
                           enrollmentId, studentId, courseId, 
                           letterGrade != null ? letterGrade.getLetterGrade() : "N/A", 
                           numericGrade, isCompleted);
    }
}
