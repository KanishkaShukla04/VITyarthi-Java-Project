package com.ccrm.core;

import com.ccrm.model.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Singleton class for centralized data management.
 * Demonstrates Singleton design pattern and thread-safe operations.
 */
public class DataStore {
    private static volatile DataStore instance;
    private static final Object lock = new Object();

    // Thread-safe collections for data storage
    private final Map<String, Student> students;
    private final Map<String, Instructor> instructors;
    private final Map<String, Course> courses;
    private final Map<String, Enrollment> enrollments;

    // Configuration constants
    private static final int MAX_CREDITS_PER_SEMESTER = 18;

    private DataStore() {
        this.students = new ConcurrentHashMap<>();
        this.instructors = new ConcurrentHashMap<>();
        this.courses = new ConcurrentHashMap<>();
        this.enrollments = new ConcurrentHashMap<>();
    }

    /**
     * Thread-safe singleton instance creation.
     * @return The singleton DataStore instance
     */
    public static DataStore getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new DataStore();
                }
            }
        }
        return instance;
    }

    // Student management methods
    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }

    public Student getStudent(String studentId) {
        return students.get(studentId);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    public List<Student> getActiveStudents() {
        return students.values().stream()
                .filter(Student::isActive)
                .collect(Collectors.toList());
    }

    public void removeStudent(String studentId) {
        students.remove(studentId);
    }

    // Instructor management methods
    public void addInstructor(Instructor instructor) {
        instructors.put(instructor.getId(), instructor);
    }

    public Instructor getInstructor(String instructorId) {
        return instructors.get(instructorId);
    }

    public List<Instructor> getAllInstructors() {
        return new ArrayList<>(instructors.values());
    }

    public List<Instructor> getActiveInstructors() {
        return instructors.values().stream()
                .filter(Instructor::isActive)
                .collect(Collectors.toList());
    }

    public void removeInstructor(String instructorId) {
        instructors.remove(instructorId);
    }

    // Course management methods
    public void addCourse(Course course) {
        courses.put(course.getCourseId(), course);
    }

    public Course getCourse(String courseId) {
        return courses.get(courseId);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }

    public List<Course> getActiveCourses() {
        return courses.values().stream()
                .filter(Course::isActive)
                .collect(Collectors.toList());
    }

    public List<Course> getCoursesByDepartment(String department) {
        return courses.values().stream()
                .filter(course -> course.getDepartment().name().equals(department))
                .collect(Collectors.toList());
    }

    public List<Course> getCoursesBySemester(String semester) {
        return courses.values().stream()
                .filter(course -> course.getSemester().name().equals(semester))
                .collect(Collectors.toList());
    }

    public void removeCourse(String courseId) {
        courses.remove(courseId);
    }

    // Enrollment management methods
    public void addEnrollment(Enrollment enrollment) {
        enrollments.put(enrollment.getEnrollmentId(), enrollment);
    }

    public Enrollment getEnrollment(String enrollmentId) {
        return enrollments.get(enrollmentId);
    }

    public List<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments.values());
    }

    public List<Enrollment> getEnrollmentsByStudent(String studentId) {
        return enrollments.values().stream()
                .filter(enrollment -> enrollment.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public List<Enrollment> getEnrollmentsByCourse(String courseId) {
        return enrollments.values().stream()
                .filter(enrollment -> enrollment.getCourseId().equals(courseId))
                .collect(Collectors.toList());
    }

    public List<Enrollment> getActiveEnrollments() {
        return enrollments.values().stream()
                .filter(Enrollment::isActive)
                .collect(Collectors.toList());
    }

    public void removeEnrollment(String enrollmentId) {
        enrollments.remove(enrollmentId);
    }

    // Business logic methods
    public int calculateStudentCredits(String studentId) {
        return enrollments.values().stream()
                .filter(enrollment -> enrollment.getStudentId().equals(studentId))
                .filter(Enrollment::isActive)
                .mapToInt(enrollment -> {
                    Course course = courses.get(enrollment.getCourseId());
                    return course != null ? course.getCreditHours() : 0;
                })
                .sum();
    }

    public double calculateStudentGPA(String studentId) {
        List<Enrollment> completedEnrollments = enrollments.values().stream()
                .filter(enrollment -> enrollment.getStudentId().equals(studentId))
                .filter(Enrollment::isCompleted)
                .collect(Collectors.toList());

        if (completedEnrollments.isEmpty()) {
            return 0.0;
        }

        double totalGradePoints = 0.0;
        int totalCredits = 0;

        for (Enrollment enrollment : completedEnrollments) {
            Course course = courses.get(enrollment.getCourseId());
            if (course != null) {
                totalGradePoints += enrollment.getLetterGrade().getGradePoints() * course.getCreditHours();
                totalCredits += course.getCreditHours();
            }
        }

        return totalCredits > 0 ? totalGradePoints / totalCredits : 0.0;
    }

    public boolean canEnrollStudent(String studentId, String courseId) {
        Student student = students.get(studentId);
        Course course = courses.get(courseId);
        
        if (student == null || course == null || !student.isActive() || !course.isActive()) {
            return false;
        }

        // Check if already enrolled
        boolean alreadyEnrolled = enrollments.values().stream()
                .anyMatch(enrollment -> enrollment.getStudentId().equals(studentId) 
                                     && enrollment.getCourseId().equals(courseId) 
                                     && enrollment.isActive());

        if (alreadyEnrolled) {
            return false;
        }

        // Check credit limit
        int currentCredits = calculateStudentCredits(studentId);
        return (currentCredits + course.getCreditHours()) <= MAX_CREDITS_PER_SEMESTER;
    }

    // Utility methods
    public void clearAllData() {
        students.clear();
        instructors.clear();
        courses.clear();
        enrollments.clear();
    }

    public Map<String, Integer> getDataCounts() {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("students", students.size());
        counts.put("instructors", instructors.size());
        counts.put("courses", courses.size());
        counts.put("enrollments", enrollments.size());
        return counts;
    }

    public static int getMaxCreditsPerSemester() {
        return MAX_CREDITS_PER_SEMESTER;
    }
}
