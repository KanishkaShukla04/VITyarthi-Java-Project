package com.ccrm.model;

import com.ccrm.enums.Department;
import com.ccrm.enums.Semester;

/**
 * Course class representing academic courses.
 * Demonstrates encapsulation and association with other entities.
 */
public class Course {
    private String courseId;
    private String courseCode;
    private String title;
    private int creditHours;
    private String instructorId;
    private Semester semester;
    private Department department;
    private boolean isActive;
    private String description;

    public Course(String courseId, String courseCode, String title, int creditHours, 
                  String instructorId, Semester semester, Department department) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.title = title;
        this.creditHours = creditHours;
        this.instructorId = instructorId;
        this.semester = semester;
        this.department = department;
        this.isActive = true;
    }

    // Getters and setters demonstrating encapsulation
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Course[ID: %s, Code: %s, Title: %s, Credits: %d, Dept: %s, Semester: %s, Active: %s]", 
                           courseId, courseCode, title, creditHours, department, semester, isActive);
    }
}
