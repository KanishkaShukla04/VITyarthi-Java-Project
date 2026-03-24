package com.ccrm.builders;

import com.ccrm.model.Student;
import com.ccrm.model.Enrollment;
import com.ccrm.model.Course;
import com.ccrm.core.DataStore;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Builder pattern implementation for Transcript objects.
 * Demonstrates Builder design pattern for complex report generation.
 */
public class TranscriptBuilder {
    private Student student;
    private List<Enrollment> enrollments;
    private boolean includeInactiveCourses;
    private boolean includeGPA;
    private boolean includeSummary;

    public TranscriptBuilder() {
        this.enrollments = new ArrayList<>();
        this.includeInactiveCourses = false;
        this.includeGPA = true;
        this.includeSummary = true;
    }

    public TranscriptBuilder student(Student student) {
        this.student = student;
        return this;
    }

    public TranscriptBuilder includeInactiveCourses(boolean includeInactiveCourses) {
        this.includeInactiveCourses = includeInactiveCourses;
        return this;
    }

    public TranscriptBuilder includeGPA(boolean includeGPA) {
        this.includeGPA = includeGPA;
        return this;
    }

    public TranscriptBuilder includeSummary(boolean includeSummary) {
        this.includeSummary = includeSummary;
        return this;
    }

    public Transcript build() {
        validateRequiredFields();
        
        DataStore dataStore = DataStore.getInstance();
        
        // Get all enrollments for the student
        List<Enrollment> studentEnrollments = dataStore.getEnrollmentsByStudent(student.getId());
        
        // Filter based on preferences
        if (!includeInactiveCourses) {
            studentEnrollments = studentEnrollments.stream()
                    .filter(Enrollment::isActive)
                    .collect(Collectors.toList());
        }
        
        this.enrollments = studentEnrollments;
        
        return new Transcript(student, enrollments, includeGPA, includeSummary);
    }

    private void validateRequiredFields() {
        if (student == null) {
            throw new IllegalArgumentException("Student is required to build transcript");
        }
    }

    /**
     * Transcript class representing a student's academic record.
     */
    public static class Transcript {
        private final Student student;
        private final List<Enrollment> enrollments;
        private final boolean includeGPA;
        private final boolean includeSummary;

        public Transcript(Student student, List<Enrollment> enrollments, 
                         boolean includeGPA, boolean includeSummary) {
            this.student = student;
            this.enrollments = enrollments;
            this.includeGPA = includeGPA;
            this.includeSummary = includeSummary;
        }

        public Student getStudent() {
            return student;
        }

        public List<Enrollment> getEnrollments() {
            return enrollments;
        }

        public boolean isIncludeGPA() {
            return includeGPA;
        }

        public boolean isIncludeSummary() {
            return includeSummary;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== ACADEMIC TRANSCRIPT ===\n");
            sb.append("Student: ").append(student.getFullName()).append("\n");
            sb.append("Registration Number: ").append(student.getRegistrationNumber()).append("\n");
            sb.append("Email: ").append(student.getEmail()).append("\n");
            sb.append("Enrollment Date: ").append(student.getEnrollmentDate()).append("\n\n");

            if (includeSummary) {
                sb.append("=== SUMMARY ===\n");
                sb.append("Total Courses: ").append(enrollments.size()).append("\n");
                sb.append("Completed Courses: ").append(
                    enrollments.stream().filter(Enrollment::isCompleted).count()
                ).append("\n");
                sb.append("Active Enrollments: ").append(
                    enrollments.stream().filter(Enrollment::isActive).count()
                ).append("\n\n");
            }

            sb.append("=== COURSE RECORD ===\n");
            DataStore dataStore = DataStore.getInstance();
            
            for (Enrollment enrollment : enrollments) {
                Course course = dataStore.getCourse(enrollment.getCourseId());
                if (course != null) {
                    sb.append(String.format("%s - %s (%d credits)\n", 
                        course.getCourseCode(), course.getTitle(), course.getCreditHours()));
                    sb.append(String.format("  Grade: %s (%.1f)\n", 
                        enrollment.getLetterGrade() != null ? enrollment.getLetterGrade().getLetterGrade() : "N/A",
                        enrollment.getNumericGrade()));
                    sb.append(String.format("  Status: %s\n", 
                        enrollment.isCompleted() ? "Completed" : "In Progress"));
                    sb.append(String.format("  Enrollment Date: %s\n\n", enrollment.getEnrollmentDate()));
                }
            }

            if (includeGPA) {
                double gpa = dataStore.calculateStudentGPA(student.getId());
                sb.append("=== GPA ===\n");
                sb.append(String.format("Current GPA: %.2f\n", gpa));
            }

            return sb.toString();
        }
    }
}
