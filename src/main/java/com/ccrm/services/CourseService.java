package com.ccrm.services;

import com.ccrm.core.DataStore;
import com.ccrm.model.Course;
import com.ccrm.exceptions.CourseNotFoundException;
import com.ccrm.interfaces.Searchable;
import com.ccrm.interfaces.Searchable.SearchCriteria;
import com.ccrm.interfaces.Searchable.Predicate;
import com.ccrm.enums.Department;
import com.ccrm.enums.Semester;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for course management operations.
 * Demonstrates service layer pattern and business logic encapsulation.
 */
public class CourseService implements Searchable<Course> {
    private final DataStore dataStore;

    public CourseService() {
        this.dataStore = DataStore.getInstance();
    }

    /**
     * Creates a new course.
     * @param course The course to create
     * @return The created course
     */
    public Course createCourse(Course course) {
        dataStore.addCourse(course);
        return course;
    }

    /**
     * Retrieves a course by ID.
     * @param courseId The course ID
     * @return The course
     * @throws CourseNotFoundException if course is not found
     */
    public Course getCourseById(String courseId) throws CourseNotFoundException {
        Course course = dataStore.getCourse(courseId);
        if (course == null) {
            throw new CourseNotFoundException(courseId);
        }
        return course;
    }

    /**
     * Retrieves all courses.
     * @return List of all courses
     */
    public List<Course> getAllCourses() {
        return dataStore.getAllCourses();
    }

    /**
     * Retrieves all active courses.
     * @return List of active courses
     */
    public List<Course> getActiveCourses() {
        return dataStore.getActiveCourses();
    }

    /**
     * Updates a course.
     * @param course The course to update
     * @return The updated course
     * @throws CourseNotFoundException if course is not found
     */
    public Course updateCourse(Course course) throws CourseNotFoundException {
        Course existingCourse = getCourseById(course.getCourseId());
        dataStore.addCourse(course); // Replace existing
        return course;
    }

    /**
     * Deactivates a course.
     * @param courseId The course ID
     * @throws CourseNotFoundException if course is not found
     */
    public void deactivateCourse(String courseId) throws CourseNotFoundException {
        Course course = getCourseById(courseId);
        course.setActive(false);
    }

    /**
     * Gets courses by department.
     * @param department The department
     * @return List of courses in the department
     */
    public List<Course> getCoursesByDepartment(Department department) {
        return dataStore.getCoursesByDepartment(department.name());
    }

    /**
     * Gets courses by semester.
     * @param semester The semester
     * @return List of courses in the semester
     */
    public List<Course> getCoursesBySemester(Semester semester) {
        return dataStore.getCoursesBySemester(semester.name());
    }

    /**
     * Gets courses by instructor.
     * @param instructorId The instructor ID
     * @return List of courses taught by the instructor
     */
    public List<Course> getCoursesByInstructor(String instructorId) {
        return dataStore.getAllCourses().stream()
                .filter(course -> course.getInstructorId().equals(instructorId))
                .collect(Collectors.toList());
    }

    /**
     * Gets enrollment count for a course.
     * @param courseId The course ID
     * @return Number of enrolled students
     */
    public int getEnrollmentCount(String courseId) {
        return dataStore.getEnrollmentsByCourse(courseId).size();
    }

    /**
     * Checks if a course has available seats.
     * @param courseId The course ID
     * @param maxCapacity Maximum capacity (if applicable)
     * @return true if course has available seats
     */
    public boolean hasAvailableSeats(String courseId, int maxCapacity) {
        int currentEnrollments = getEnrollmentCount(courseId);
        return currentEnrollments < maxCapacity;
    }

    // Searchable interface implementation
    @Override
    public List<Course> searchByField(String field, String value) {
        return dataStore.getAllCourses().stream()
                .filter(course -> {
                    switch (field.toLowerCase()) {
                        case "courseid":
                        case "id":
                            return course.getCourseId().toLowerCase().contains(value.toLowerCase());
                        case "coursecode":
                        case "code":
                            return course.getCourseCode().toLowerCase().contains(value.toLowerCase());
                        case "title":
                            return course.getTitle().toLowerCase().contains(value.toLowerCase());
                        case "instructorid":
                        case "instructor":
                            return course.getInstructorId().toLowerCase().contains(value.toLowerCase());
                        case "department":
                            return course.getDepartment().name().toLowerCase().contains(value.toLowerCase());
                        case "semester":
                            return course.getSemester().name().toLowerCase().contains(value.toLowerCase());
                        case "active":
                            return String.valueOf(course.isActive()).equalsIgnoreCase(value);
                        case "credits":
                        case "credithours":
                            return String.valueOf(course.getCreditHours()).equals(value);
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> searchByCriteria(SearchCriteria criteria) {
        return dataStore.getAllCourses().stream()
                .filter(course -> {
                    String fieldValue = getFieldValue(course, criteria.getField());
                    return matchesCriteria(fieldValue, criteria.getValue(), criteria.getOperator());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> filter(Predicate<Course> predicate) {
        return dataStore.getAllCourses().stream()
                .filter(predicate::test)
                .collect(Collectors.toList());
    }

    /**
     * Gets the value of a field from a course object.
     * @param course The course object
     * @param field The field name
     * @return The field value as string
     */
    private String getFieldValue(Course course, String field) {
        switch (field.toLowerCase()) {
            case "courseid":
            case "id":
                return course.getCourseId();
            case "coursecode":
            case "code":
                return course.getCourseCode();
            case "title":
                return course.getTitle();
            case "instructorid":
            case "instructor":
                return course.getInstructorId();
            case "department":
                return course.getDepartment().name();
            case "semester":
                return course.getSemester().name();
            case "active":
                return String.valueOf(course.isActive());
            case "credits":
            case "credithours":
                return String.valueOf(course.getCreditHours());
            case "description":
                return course.getDescription() != null ? course.getDescription() : "";
            default:
                return "";
        }
    }

    /**
     * Checks if a field value matches the criteria.
     * @param fieldValue The field value
     * @param searchValue The search value
     * @param operator The search operator
     * @return true if matches
     */
    private boolean matchesCriteria(String fieldValue, String searchValue, Searchable.SearchOperator operator) {
        switch (operator) {
            case EQUALS:
                return fieldValue.equalsIgnoreCase(searchValue);
            case CONTAINS:
                return fieldValue.toLowerCase().contains(searchValue.toLowerCase());
            case STARTS_WITH:
                return fieldValue.toLowerCase().startsWith(searchValue.toLowerCase());
            case ENDS_WITH:
                return fieldValue.toLowerCase().endsWith(searchValue.toLowerCase());
            case GREATER_THAN:
                try {
                    return Double.parseDouble(fieldValue) > Double.parseDouble(searchValue);
                } catch (NumberFormatException e) {
                    return false;
                }
            case LESS_THAN:
                try {
                    return Double.parseDouble(fieldValue) < Double.parseDouble(searchValue);
                } catch (NumberFormatException e) {
                    return false;
                }
            default:
                return false;
        }
    }
}
