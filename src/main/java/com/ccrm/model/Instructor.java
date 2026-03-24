package com.ccrm.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Instructor class extending Person, demonstrating inheritance.
 * Manages instructor-specific data and assigned courses.
 */
public class Instructor extends Person {
    private String employeeId;
    private String department;
    private String title;
    private LocalDate hireDate;
    private boolean isActive;
    private List<String> assignedCourseIds;

    public Instructor(String id, String employeeId, String fullName, String email, String department) {
        super(id, fullName, email);
        this.employeeId = employeeId;
        this.department = department;
        this.hireDate = LocalDate.now();
        this.isActive = true;
        this.assignedCourseIds = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "Instructor";
    }

    public void assignCourse(String courseId) {
        if (!assignedCourseIds.contains(courseId)) {
            assignedCourseIds.add(courseId);
        }
    }

    public void unassignCourse(String courseId) {
        assignedCourseIds.remove(courseId);
    }

    public boolean isAssignedToCourse(String courseId) {
        return assignedCourseIds.contains(courseId);
    }

    // Getters and setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<String> getAssignedCourseIds() {
        return new ArrayList<>(assignedCourseIds);
    }

    public void setAssignedCourseIds(List<String> assignedCourseIds) {
        this.assignedCourseIds = new ArrayList<>(assignedCourseIds);
    }

    @Override
    public String toString() {
        return String.format("Instructor[ID: %s, EmpID: %s, Name: %s, Email: %s, Dept: %s, Active: %s]", 
                           getId(), employeeId, getFullName(), getEmail(), department, isActive);
    }
}
