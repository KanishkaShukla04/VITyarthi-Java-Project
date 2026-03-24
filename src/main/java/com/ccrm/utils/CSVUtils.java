package com.ccrm.utils;

import com.ccrm.model.*;
import com.ccrm.enums.Department;
import com.ccrm.enums.Semester;
import com.ccrm.enums.Grade;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for CSV import/export operations.
 * Demonstrates file I/O operations and data serialization.
 */
public class CSVUtils {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String CSV_DELIMITER = ",";

    /**
     * Exports students to CSV format.
     * @param students List of students to export
     * @param filePath Output file path
     * @throws IOException if export fails
     */
    public static void exportStudentsToCSV(List<Student> students, String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("ID,RegistrationNumber,FullName,Email,DateOfBirth,PhoneNumber,EnrollmentDate,IsActive,CurrentGPA");
        
        for (Student student : students) {
            StringBuilder line = new StringBuilder();
            line.append(escapeCSV(student.getId())).append(CSV_DELIMITER);
            line.append(escapeCSV(student.getRegistrationNumber())).append(CSV_DELIMITER);
            line.append(escapeCSV(student.getFullName())).append(CSV_DELIMITER);
            line.append(escapeCSV(student.getEmail())).append(CSV_DELIMITER);
            line.append(student.getDateOfBirth() != null ? student.getDateOfBirth().format(DATE_FORMATTER) : "").append(CSV_DELIMITER);
            line.append(escapeCSV(student.getPhoneNumber())).append(CSV_DELIMITER);
            line.append(student.getEnrollmentDate().format(DATE_FORMATTER)).append(CSV_DELIMITER);
            line.append(student.isActive()).append(CSV_DELIMITER);
            line.append(student.getCurrentGPA());
            
            lines.add(line.toString());
        }
        
        Files.write(Paths.get(filePath), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Imports students from CSV format.
     * @param filePath Input file path
     * @return List of imported students
     * @throws IOException if import fails
     */
    public static List<Student> importStudentsFromCSV(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        List<Student> students = new ArrayList<>();
        
        // Skip header line
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
            
            String[] fields = parseCSVLine(line);
            if (fields.length >= 8) {
                Student student = new Student(
                    fields[0], // ID
                    fields[1], // Registration Number
                    fields[2], // Full Name
                    fields[3]  // Email
                );
                
                if (!fields[4].isEmpty()) {
                    student.setDateOfBirth(LocalDate.parse(fields[4], DATE_FORMATTER));
                }
                if (!fields[5].isEmpty()) {
                    student.setPhoneNumber(fields[5]);
                }
                if (!fields[6].isEmpty()) {
                    student.setEnrollmentDate(LocalDate.parse(fields[6], DATE_FORMATTER));
                }
                student.setActive(Boolean.parseBoolean(fields[7]));
                if (fields.length > 8 && !fields[8].isEmpty()) {
                    student.setCurrentGPA(Double.parseDouble(fields[8]));
                }
                
                students.add(student);
            }
        }
        
        return students;
    }

    /**
     * Exports courses to CSV format.
     * @param courses List of courses to export
     * @param filePath Output file path
     * @throws IOException if export fails
     */
    public static void exportCoursesToCSV(List<Course> courses, String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("CourseID,CourseCode,Title,CreditHours,InstructorID,Semester,Department,Description,IsActive");
        
        for (Course course : courses) {
            StringBuilder line = new StringBuilder();
            line.append(escapeCSV(course.getCourseId())).append(CSV_DELIMITER);
            line.append(escapeCSV(course.getCourseCode())).append(CSV_DELIMITER);
            line.append(escapeCSV(course.getTitle())).append(CSV_DELIMITER);
            line.append(course.getCreditHours()).append(CSV_DELIMITER);
            line.append(escapeCSV(course.getInstructorId())).append(CSV_DELIMITER);
            line.append(course.getSemester().name()).append(CSV_DELIMITER);
            line.append(course.getDepartment().name()).append(CSV_DELIMITER);
            line.append(escapeCSV(course.getDescription())).append(CSV_DELIMITER);
            line.append(course.isActive());
            
            lines.add(line.toString());
        }
        
        Files.write(Paths.get(filePath), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Imports courses from CSV format.
     * @param filePath Input file path
     * @return List of imported courses
     * @throws IOException if import fails
     */
    public static List<Course> importCoursesFromCSV(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        List<Course> courses = new ArrayList<>();
        
        // Skip header line
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
            
            String[] fields = parseCSVLine(line);
            if (fields.length >= 8) {
                Course course = new Course(
                    fields[0], // Course ID
                    fields[1], // Course Code
                    fields[2], // Title
                    Integer.parseInt(fields[3]), // Credit Hours
                    fields[4], // Instructor ID
                    Semester.valueOf(fields[5]), // Semester
                    Department.valueOf(fields[6]) // Department
                );
                
                if (fields.length > 7 && !fields[7].isEmpty()) {
                    course.setDescription(fields[7]);
                }
                if (fields.length > 8) {
                    course.setActive(Boolean.parseBoolean(fields[8]));
                }
                
                courses.add(course);
            }
        }
        
        return courses;
    }

    /**
     * Exports enrollments to CSV format.
     * @param enrollments List of enrollments to export
     * @param filePath Output file path
     * @throws IOException if export fails
     */
    public static void exportEnrollmentsToCSV(List<Enrollment> enrollments, String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("EnrollmentID,StudentID,CourseID,EnrollmentDate,CompletionDate,NumericGrade,LetterGrade,IsCompleted,IsActive");
        
        for (Enrollment enrollment : enrollments) {
            StringBuilder line = new StringBuilder();
            line.append(escapeCSV(enrollment.getEnrollmentId())).append(CSV_DELIMITER);
            line.append(escapeCSV(enrollment.getStudentId())).append(CSV_DELIMITER);
            line.append(escapeCSV(enrollment.getCourseId())).append(CSV_DELIMITER);
            line.append(enrollment.getEnrollmentDate().format(DATE_FORMATTER)).append(CSV_DELIMITER);
            line.append(enrollment.getCompletionDate() != null ? enrollment.getCompletionDate().format(DATE_FORMATTER) : "").append(CSV_DELIMITER);
            line.append(enrollment.getNumericGrade()).append(CSV_DELIMITER);
            line.append(enrollment.getLetterGrade() != null ? enrollment.getLetterGrade().name() : "").append(CSV_DELIMITER);
            line.append(enrollment.isCompleted()).append(CSV_DELIMITER);
            line.append(enrollment.isActive());
            
            lines.add(line.toString());
        }
        
        Files.write(Paths.get(filePath), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Imports enrollments from CSV format.
     * @param filePath Input file path
     * @return List of imported enrollments
     * @throws IOException if import fails
     */
    public static List<Enrollment> importEnrollmentsFromCSV(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        List<Enrollment> enrollments = new ArrayList<>();
        
        // Skip header line
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
            
            String[] fields = parseCSVLine(line);
            if (fields.length >= 6) {
                Enrollment enrollment = new Enrollment(
                    fields[0], // Enrollment ID
                    fields[1], // Student ID
                    fields[2]  // Course ID
                );
                
                if (!fields[3].isEmpty()) {
                    enrollment.setEnrollmentDate(LocalDate.parse(fields[3], DATE_FORMATTER));
                }
                if (!fields[4].isEmpty()) {
                    enrollment.setCompletionDate(LocalDate.parse(fields[4], DATE_FORMATTER));
                }
                if (!fields[5].isEmpty()) {
                    enrollment.setNumericGrade(Double.parseDouble(fields[5]));
                }
                if (fields.length > 6 && !fields[6].isEmpty()) {
                    enrollment.setLetterGrade(Grade.valueOf(fields[6]));
                }
                if (fields.length > 7) {
                    enrollment.setCompleted(Boolean.parseBoolean(fields[7]));
                }
                if (fields.length > 8) {
                    enrollment.setActive(Boolean.parseBoolean(fields[8]));
                }
                
                enrollments.add(enrollment);
            }
        }
        
        return enrollments;
    }

    /**
     * Escapes CSV field values.
     * @param value The value to escape
     * @return Escaped value
     */
    private static String escapeCSV(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    /**
     * Parses a CSV line handling quoted fields.
     * @param line The CSV line to parse
     * @return Array of field values
     */
    private static String[] parseCSVLine(String line) {
        List<String> fields = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    // Escaped quote
                    currentField.append('"');
                    i++; // Skip next quote
                } else {
                    // Toggle quote state
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                // Field separator
                fields.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        
        // Add the last field
        fields.add(currentField.toString());
        
        return fields.toArray(new String[0]);
    }
}
