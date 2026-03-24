package com.ccrm.builders;

import com.ccrm.model.Course;
import com.ccrm.enums.Department;
import com.ccrm.enums.Semester;

/**
 * Builder pattern implementation for Course objects.
 * Demonstrates Builder design pattern for complex object construction.
 */
public class CourseBuilder {
    private String courseId;
    private String courseCode;
    private String title;
    private int creditHours;
    private String instructorId;
    private Semester semester;
    private Department department;
    private String description;

    public CourseBuilder() {
        // Initialize with default values
        this.creditHours = 3;
        this.semester = Semester.FALL;
        this.department = Department.COMPUTER_SCIENCE;
    }

    public CourseBuilder courseId(String courseId) {
        this.courseId = courseId;
        return this;
    }

    public CourseBuilder courseCode(String courseCode) {
        this.courseCode = courseCode;
        return this;
    }

    public CourseBuilder title(String title) {
        this.title = title;
        return this;
    }

    public CourseBuilder creditHours(int creditHours) {
        this.creditHours = creditHours;
        return this;
    }

    public CourseBuilder instructorId(String instructorId) {
        this.instructorId = instructorId;
        return this;
    }

    public CourseBuilder semester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public CourseBuilder department(Department department) {
        this.department = department;
        return this;
    }

    public CourseBuilder description(String description) {
        this.description = description;
        return this;
    }

    public Course build() {
        validateRequiredFields();
        
        Course course = new Course(courseId, courseCode, title, creditHours, 
                                 instructorId, semester, department);
        course.setDescription(description);
        
        return course;
    }

    private void validateRequiredFields() {
        if (courseId == null || courseId.trim().isEmpty()) {
            throw new IllegalArgumentException("Course ID is required");
        }
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code is required");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Course title is required");
        }
        if (creditHours <= 0) {
            throw new IllegalArgumentException("Credit hours must be positive");
        }
        if (instructorId == null || instructorId.trim().isEmpty()) {
            throw new IllegalArgumentException("Instructor ID is required");
        }
    }
}
