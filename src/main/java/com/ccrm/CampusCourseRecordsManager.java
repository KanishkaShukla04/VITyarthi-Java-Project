package com.ccrm;

import com.ccrm.core.DataStore;
import com.ccrm.model.*;
import com.ccrm.enums.*;
import com.ccrm.exceptions.*;
import com.ccrm.builders.*;
import com.ccrm.utils.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main application class for Campus Course & Records Manager.
 * Demonstrates menu-driven CLI interface and application orchestration.
 */
public class CampusCourseRecordsManager {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DataStore dataStore = DataStore.getInstance();
    private static final String DATA_DIR = "data";
    private static final String BACKUP_DIR = "backups";

    public static void main(String[] args) {
        System.out.println("=== Campus Course & Records Manager (CCRM) ===");
        System.out.println("Welcome to the Campus Management System!");
        
        try {
            // Initialize directories
            FileUtils.createDirectoryIfNotExists(DATA_DIR);
            FileUtils.createDirectoryIfNotExists(BACKUP_DIR);
            
            // Load existing data
            loadInitialData();
            
            // Main menu loop
            boolean running = true;
            while (running) {
                displayMainMenu();
                int choice = getIntInput("Enter your choice: ");
                
                switch (choice) {
                    case 1:
                        studentManagementMenu();
                        break;
                    case 2:
                        courseManagementMenu();
                        break;
                    case 3:
                        enrollmentManagementMenu();
                        break;
                    case 4:
                        fileOperationsMenu();
                        break;
                    case 5:
                        systemReportsMenu();
                        break;
                    case 6:
                        backupOperationsMenu();
                        break;
                    case 0:
                        System.out.println("Thank you for using CCRM!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                
                if (running) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment & Grading");
        System.out.println("4. File Operations");
        System.out.println("5. System Reports");
        System.out.println("6. Backup Operations");
        System.out.println("0. Exit");
        System.out.println("==================");
    }

    private static void studentManagementMenu() {
        boolean backToMain = false;
        while (!backToMain) {
            System.out.println("\n=== STUDENT MANAGEMENT ===");
            System.out.println("1. Add New Student");
            System.out.println("2. View All Students");
            System.out.println("3. View Student Details");
            System.out.println("4. Update Student");
            System.out.println("5. Deactivate Student");
            System.out.println("6. Generate Student Transcript");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    viewStudentDetails();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deactivateStudent();
                    break;
                case 6:
                    generateStudentTranscript();
                    break;
                case 0:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void courseManagementMenu() {
        boolean backToMain = false;
        while (!backToMain) {
            System.out.println("\n=== COURSE MANAGEMENT ===");
            System.out.println("1. Add New Course");
            System.out.println("2. View All Courses");
            System.out.println("3. View Course Details");
            System.out.println("4. Update Course");
            System.out.println("5. Deactivate Course");
            System.out.println("6. Search Courses by Department");
            System.out.println("7. Search Courses by Semester");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    viewAllCourses();
                    break;
                case 3:
                    viewCourseDetails();
                    break;
                case 4:
                    updateCourse();
                    break;
                case 5:
                    deactivateCourse();
                    break;
                case 6:
                    searchCoursesByDepartment();
                    break;
                case 7:
                    searchCoursesBySemester();
                    break;
                case 0:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void enrollmentManagementMenu() {
        boolean backToMain = false;
        while (!backToMain) {
            System.out.println("\n=== ENROLLMENT & GRADING ===");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. Unenroll Student from Course");
            System.out.println("3. Record Grade");
            System.out.println("4. View Student Enrollments");
            System.out.println("5. View Course Enrollments");
            System.out.println("6. Calculate Student GPA");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    enrollStudent();
                    break;
                case 2:
                    unenrollStudent();
                    break;
                case 3:
                    recordGrade();
                    break;
                case 4:
                    viewStudentEnrollments();
                    break;
                case 5:
                    viewCourseEnrollments();
                    break;
                case 6:
                    calculateStudentGPA();
                    break;
                case 0:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void fileOperationsMenu() {
        boolean backToMain = false;
        while (!backToMain) {
            System.out.println("\n=== FILE OPERATIONS ===");
            System.out.println("1. Export Students to CSV");
            System.out.println("2. Export Courses to CSV");
            System.out.println("3. Export Enrollments to CSV");
            System.out.println("4. Import Students from CSV");
            System.out.println("5. Import Courses from CSV");
            System.out.println("6. Import Enrollments from CSV");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    exportStudentsToCSV();
                    break;
                case 2:
                    exportCoursesToCSV();
                    break;
                case 3:
                    exportEnrollmentsToCSV();
                    break;
                case 4:
                    importStudentsFromCSV();
                    break;
                case 5:
                    importCoursesFromCSV();
                    break;
                case 6:
                    importEnrollmentsFromCSV();
                    break;
                case 0:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void systemReportsMenu() {
        boolean backToMain = false;
        while (!backToMain) {
            System.out.println("\n=== SYSTEM REPORTS ===");
            System.out.println("1. System Statistics");
            System.out.println("2. GPA Distribution Report");
            System.out.println("3. Course Enrollment Statistics");
            System.out.println("4. Department Statistics");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    displaySystemStatistics();
                    break;
                case 2:
                    displayGPADistribution();
                    break;
                case 3:
                    displayCourseEnrollmentStats();
                    break;
                case 4:
                    displayDepartmentStats();
                    break;
                case 0:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void backupOperationsMenu() {
        boolean backToMain = false;
        while (!backToMain) {
            System.out.println("\n=== BACKUP OPERATIONS ===");
            System.out.println("1. Create Backup");
            System.out.println("2. List Backup Files");
            System.out.println("3. Calculate Backup Size");
            System.out.println("4. Cleanup Old Backups");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    createBackup();
                    break;
                case 2:
                    listBackupFiles();
                    break;
                case 3:
                    calculateBackupSize();
                    break;
                case 4:
                    cleanupOldBackups();
                    break;
                case 0:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Student Management Methods
    private static void addStudent() {
        System.out.println("\n=== ADD NEW STUDENT ===");
        String id = getStringInput("Enter Student ID: ");
        String regNo = getStringInput("Enter Registration Number: ");
        String fullName = getStringInput("Enter Full Name: ");
        String email = getStringInput("Enter Email: ");
        
        Student student = new Student(id, regNo, fullName, email);
        dataStore.addStudent(student);
        System.out.println("Student added successfully!");
    }

    private static void viewAllStudents() {
        System.out.println("\n=== ALL STUDENTS ===");
        List<Student> students = dataStore.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(System.out::println);
        }
    }

    private static void viewStudentDetails() {
        String studentId = getStringInput("Enter Student ID: ");
        Student student = dataStore.getStudent(studentId);
        if (student == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println("\n=== STUDENT DETAILS ===");
            System.out.println(student);
            System.out.println("Enrolled Courses: " + student.getEnrolledCourseIds().size());
            System.out.println("Current GPA: " + String.format("%.2f", student.getCurrentGPA()));
        }
    }

    private static void updateStudent() {
        String studentId = getStringInput("Enter Student ID: ");
        Student student = dataStore.getStudent(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        System.out.println("Current student: " + student);
        System.out.println("Enter new values (press Enter to keep current value):");
        
        String newName = getStringInput("Full Name [" + student.getFullName() + "]: ");
        if (!newName.isEmpty()) student.setFullName(newName);
        
        String newEmail = getStringInput("Email [" + student.getEmail() + "]: ");
        if (!newEmail.isEmpty()) student.setEmail(newEmail);
        
        System.out.println("Student updated successfully!");
    }

    private static void deactivateStudent() {
        String studentId = getStringInput("Enter Student ID: ");
        Student student = dataStore.getStudent(studentId);
        if (student == null) {
            System.out.println("Student not found.");
        } else {
            student.setActive(false);
            System.out.println("Student deactivated successfully!");
        }
    }

    private static void generateStudentTranscript() {
        String studentId = getStringInput("Enter Student ID: ");
        Student student = dataStore.getStudent(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        boolean includeInactive = getBooleanInput("Include inactive courses? (y/n): ");
        
        TranscriptBuilder.Transcript transcript = new TranscriptBuilder()
                .student(student)
                .includeInactiveCourses(includeInactive)
                .build();
        
        System.out.println("\n" + transcript);
    }

    // Course Management Methods
    private static void addCourse() {
        System.out.println("\n=== ADD NEW COURSE ===");
        String courseId = getStringInput("Enter Course ID: ");
        String courseCode = getStringInput("Enter Course Code: ");
        String title = getStringInput("Enter Course Title: ");
        int creditHours = getIntInput("Enter Credit Hours: ");
        String instructorId = getStringInput("Enter Instructor ID: ");
        
        System.out.println("Available Semesters:");
        for (Semester semester : Semester.values()) {
            System.out.println(semester.ordinal() + 1 + ". " + semester.getDisplayName());
        }
        int semesterChoice = getIntInput("Select Semester: ") - 1;
        Semester semester = Semester.values()[semesterChoice];
        
        System.out.println("Available Departments:");
        for (Department dept : Department.values()) {
            System.out.println(dept.ordinal() + 1 + ". " + dept.getFullName());
        }
        int deptChoice = getIntInput("Select Department: ") - 1;
        Department department = Department.values()[deptChoice];
        
        Course course = new CourseBuilder()
                .courseId(courseId)
                .courseCode(courseCode)
                .title(title)
                .creditHours(creditHours)
                .instructorId(instructorId)
                .semester(semester)
                .department(department)
                .build();
        
        dataStore.addCourse(course);
        System.out.println("Course added successfully!");
    }

    private static void viewAllCourses() {
        System.out.println("\n=== ALL COURSES ===");
        List<Course> courses = dataStore.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            courses.forEach(System.out::println);
        }
    }

    private static void viewCourseDetails() {
        String courseId = getStringInput("Enter Course ID: ");
        Course course = dataStore.getCourse(courseId);
        if (course == null) {
            System.out.println("Course not found.");
        } else {
            System.out.println("\n=== COURSE DETAILS ===");
            System.out.println(course);
            System.out.println("Enrolled Students: " + dataStore.getEnrollmentsByCourse(courseId).size());
        }
    }

    private static void updateCourse() {
        String courseId = getStringInput("Enter Course ID: ");
        Course course = dataStore.getCourse(courseId);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        
        System.out.println("Current course: " + course);
        System.out.println("Enter new values (press Enter to keep current value):");
        
        String newTitle = getStringInput("Title [" + course.getTitle() + "]: ");
        if (!newTitle.isEmpty()) course.setTitle(newTitle);
        
        String newDescription = getStringInput("Description [" + course.getDescription() + "]: ");
        if (!newDescription.isEmpty()) course.setDescription(newDescription);
        
        System.out.println("Course updated successfully!");
    }

    private static void deactivateCourse() {
        String courseId = getStringInput("Enter Course ID: ");
        Course course = dataStore.getCourse(courseId);
        if (course == null) {
            System.out.println("Course not found.");
        } else {
            course.setActive(false);
            System.out.println("Course deactivated successfully!");
        }
    }

    private static void searchCoursesByDepartment() {
        System.out.println("Available Departments:");
        for (Department dept : Department.values()) {
            System.out.println(dept.ordinal() + 1 + ". " + dept.getFullName());
        }
        int choice = getIntInput("Select Department: ") - 1;
        Department department = Department.values()[choice];
        
        List<Course> courses = dataStore.getCoursesByDepartment(department.name());
        System.out.println("\n=== COURSES IN " + department.getFullName() + " ===");
        if (courses.isEmpty()) {
            System.out.println("No courses found in this department.");
        } else {
            courses.forEach(System.out::println);
        }
    }

    private static void searchCoursesBySemester() {
        System.out.println("Available Semesters:");
        for (Semester semester : Semester.values()) {
            System.out.println(semester.ordinal() + 1 + ". " + semester.getDisplayName());
        }
        int choice = getIntInput("Select Semester: ") - 1;
        Semester semester = Semester.values()[choice];
        
        List<Course> courses = dataStore.getCoursesBySemester(semester.name());
        System.out.println("\n=== COURSES IN " + semester.getDisplayName() + " ===");
        if (courses.isEmpty()) {
            System.out.println("No courses found in this semester.");
        } else {
            courses.forEach(System.out::println);
        }
    }

    // Enrollment Management Methods
    private static void enrollStudent() {
        String studentId = getStringInput("Enter Student ID: ");
        String courseId = getStringInput("Enter Course ID: ");
        
        if (dataStore.canEnrollStudent(studentId, courseId)) {
            String enrollmentId = UUID.randomUUID().toString();
            Enrollment enrollment = new Enrollment(enrollmentId, studentId, courseId);
            dataStore.addEnrollment(enrollment);
            
            // Update student's enrolled courses
            Student student = dataStore.getStudent(studentId);
            student.enrollInCourse(courseId);
            
            System.out.println("Student enrolled successfully!");
        } else {
            System.out.println("Cannot enroll student. Check if student/course exists and credit limit.");
        }
    }

    private static void unenrollStudent() {
        String studentId = getStringInput("Enter Student ID: ");
        String courseId = getStringInput("Enter Course ID: ");
        
        List<Enrollment> enrollments = dataStore.getEnrollmentsByStudent(studentId);
        Enrollment enrollment = enrollments.stream()
                .filter(e -> e.getCourseId().equals(courseId) && e.isActive())
                .findFirst()
                .orElse(null);
        
        if (enrollment == null) {
            System.out.println("Active enrollment not found.");
        } else {
            enrollment.withdraw();
            Student student = dataStore.getStudent(studentId);
            student.unenrollFromCourse(courseId);
            System.out.println("Student unenrolled successfully!");
        }
    }

    private static void recordGrade() {
        String studentId = getStringInput("Enter Student ID: ");
        String courseId = getStringInput("Enter Course ID: ");
        
        List<Enrollment> enrollments = dataStore.getEnrollmentsByStudent(studentId);
        Enrollment enrollment = enrollments.stream()
                .filter(e -> e.getCourseId().equals(courseId) && e.isActive())
                .findFirst()
                .orElse(null);
        
        if (enrollment == null) {
            System.out.println("Active enrollment not found.");
            return;
        }
        
        try {
            double grade = getDoubleInput("Enter numeric grade (0-100): ");
            if (grade < 0 || grade > 100) {
                throw new InvalidGradeException(grade);
            }
            
            enrollment.recordGrade(grade);
            
            // Update student's GPA
            Student student = dataStore.getStudent(studentId);
            double newGPA = dataStore.calculateStudentGPA(studentId);
            student.setCurrentGPA(newGPA);
            
            System.out.println("Grade recorded successfully!");
            System.out.println("Letter Grade: " + enrollment.getLetterGrade().getLetterGrade());
        } catch (InvalidGradeException e) {
            System.out.println("Invalid grade: " + e.getMessage());
        }
    }

    private static void viewStudentEnrollments() {
        String studentId = getStringInput("Enter Student ID: ");
        List<Enrollment> enrollments = dataStore.getEnrollmentsByStudent(studentId);
        
        System.out.println("\n=== STUDENT ENROLLMENTS ===");
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
        } else {
            enrollments.forEach(System.out::println);
        }
    }

    private static void viewCourseEnrollments() {
        String courseId = getStringInput("Enter Course ID: ");
        List<Enrollment> enrollments = dataStore.getEnrollmentsByCourse(courseId);
        
        System.out.println("\n=== COURSE ENROLLMENTS ===");
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
        } else {
            enrollments.forEach(System.out::println);
        }
    }

    private static void calculateStudentGPA() {
        String studentId = getStringInput("Enter Student ID: ");
        double gpa = dataStore.calculateStudentGPA(studentId);
        
        System.out.println("\n=== STUDENT GPA ===");
        System.out.println("Student ID: " + studentId);
        System.out.println("Current GPA: " + String.format("%.2f", gpa));
    }

    // File Operations Methods
    private static void exportStudentsToCSV() {
        String filePath = DATA_DIR + "/students.csv";
        try {
            CSVUtils.exportStudentsToCSV(dataStore.getAllStudents(), filePath);
            System.out.println("Students exported to " + filePath);
        } catch (Exception e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }

    private static void exportCoursesToCSV() {
        String filePath = DATA_DIR + "/courses.csv";
        try {
            CSVUtils.exportCoursesToCSV(dataStore.getAllCourses(), filePath);
            System.out.println("Courses exported to " + filePath);
        } catch (Exception e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }

    private static void exportEnrollmentsToCSV() {
        String filePath = DATA_DIR + "/enrollments.csv";
        try {
            CSVUtils.exportEnrollmentsToCSV(dataStore.getAllEnrollments(), filePath);
            System.out.println("Enrollments exported to " + filePath);
        } catch (Exception e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }

    private static void importStudentsFromCSV() {
        String filePath = getStringInput("Enter CSV file path: ");
        try {
            List<Student> students = CSVUtils.importStudentsFromCSV(filePath);
            for (Student student : students) {
                dataStore.addStudent(student);
            }
            System.out.println("Imported " + students.size() + " students successfully!");
        } catch (Exception e) {
            System.out.println("Import failed: " + e.getMessage());
        }
    }

    private static void importCoursesFromCSV() {
        String filePath = getStringInput("Enter CSV file path: ");
        try {
            List<Course> courses = CSVUtils.importCoursesFromCSV(filePath);
            for (Course course : courses) {
                dataStore.addCourse(course);
            }
            System.out.println("Imported " + courses.size() + " courses successfully!");
        } catch (Exception e) {
            System.out.println("Import failed: " + e.getMessage());
        }
    }

    private static void importEnrollmentsFromCSV() {
        String filePath = getStringInput("Enter CSV file path: ");
        try {
            List<Enrollment> enrollments = CSVUtils.importEnrollmentsFromCSV(filePath);
            for (Enrollment enrollment : enrollments) {
                dataStore.addEnrollment(enrollment);
            }
            System.out.println("Imported " + enrollments.size() + " enrollments successfully!");
        } catch (Exception e) {
            System.out.println("Import failed: " + e.getMessage());
        }
    }

    // System Reports Methods
    private static void displaySystemStatistics() {
        System.out.println("\n=== SYSTEM STATISTICS ===");
        Map<String, Integer> counts = dataStore.getDataCounts();
        counts.forEach((key, value) -> 
            System.out.println(key.substring(0, 1).toUpperCase() + key.substring(1) + ": " + value));
        
        System.out.println("Max Credits per Semester: " + DataStore.getMaxCreditsPerSemester());
    }

    private static void displayGPADistribution() {
        System.out.println("\n=== GPA DISTRIBUTION ===");
        List<Student> students = dataStore.getAllStudents();
        
        Map<String, Long> gpaDistribution = students.stream()
                .collect(Collectors.groupingBy(
                    student -> {
                        double gpa = student.getCurrentGPA();
                        if (gpa >= 3.7) return "A (3.7-4.0)";
                        else if (gpa >= 3.0) return "B (3.0-3.6)";
                        else if (gpa >= 2.0) return "C (2.0-2.9)";
                        else if (gpa >= 1.0) return "D (1.0-1.9)";
                        else return "F (0.0-0.9)";
                    },
                    Collectors.counting()
                ));
        
        gpaDistribution.forEach((range, count) -> 
            System.out.println(range + ": " + count + " students"));
    }

    private static void displayCourseEnrollmentStats() {
        System.out.println("\n=== COURSE ENROLLMENT STATISTICS ===");
        List<Course> courses = dataStore.getAllCourses();
        
        courses.forEach(course -> {
            int enrollmentCount = dataStore.getEnrollmentsByCourse(course.getCourseId()).size();
            System.out.println(course.getCourseCode() + " - " + course.getTitle() + 
                             ": " + enrollmentCount + " students");
        });
    }

    private static void displayDepartmentStats() {
        System.out.println("\n=== DEPARTMENT STATISTICS ===");
        Map<String, Long> deptStats = dataStore.getAllCourses().stream()
                .collect(Collectors.groupingBy(
                    course -> course.getDepartment().getFullName(),
                    Collectors.counting()
                ));
        
        deptStats.forEach((dept, count) -> 
            System.out.println(dept + ": " + count + " courses"));
    }

    // Backup Operations Methods
    private static void createBackup() {
        try {
            String backupDir = BackupUtils.createBackup(DATA_DIR, BACKUP_DIR);
            System.out.println("Backup created successfully at: " + backupDir);
        } catch (Exception e) {
            System.out.println("Backup creation failed: " + e.getMessage());
        }
    }

    private static void listBackupFiles() {
        try {
            List<String> files = FileUtils.listFilesRecursively(BACKUP_DIR);
            System.out.println("\n=== BACKUP FILES ===");
            if (files.isEmpty()) {
                System.out.println("No backup files found.");
            } else {
                files.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Failed to list backup files: " + e.getMessage());
        }
    }

    private static void calculateBackupSize() {
        try {
            BackupUtils.FileCountInfo info = BackupUtils.countFilesRecursively(BACKUP_DIR);
            System.out.println("\n=== BACKUP SIZE INFORMATION ===");
            System.out.println(info);
        } catch (Exception e) {
            System.out.println("Failed to calculate backup size: " + e.getMessage());
        }
    }

    private static void cleanupOldBackups() {
        int days = getIntInput("Enter maximum age in days for backups to keep: ");
        try {
            int deletedCount = BackupUtils.cleanupOldBackups(BACKUP_DIR, days);
            System.out.println("Cleaned up " + deletedCount + " old backup directories.");
        } catch (Exception e) {
            System.out.println("Cleanup failed: " + e.getMessage());
        }
    }

    // Utility Methods
    private static void loadInitialData() {
        try {
            // Try to load existing data files
            if (FileUtils.fileExists(DATA_DIR + "/students.csv")) {
                List<Student> students = CSVUtils.importStudentsFromCSV(DATA_DIR + "/students.csv");
                students.forEach(dataStore::addStudent);
                System.out.println("Loaded " + students.size() + " students from file.");
            }
            
            if (FileUtils.fileExists(DATA_DIR + "/courses.csv")) {
                List<Course> courses = CSVUtils.importCoursesFromCSV(DATA_DIR + "/courses.csv");
                courses.forEach(dataStore::addCourse);
                System.out.println("Loaded " + courses.size() + " courses from file.");
            }
            
            if (FileUtils.fileExists(DATA_DIR + "/enrollments.csv")) {
                List<Enrollment> enrollments = CSVUtils.importEnrollmentsFromCSV(DATA_DIR + "/enrollments.csv");
                enrollments.forEach(dataStore::addEnrollment);
                System.out.println("Loaded " + enrollments.size() + " enrollments from file.");
            }
        } catch (Exception e) {
            System.out.println("Note: Could not load existing data files. Starting with empty system.");
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static boolean getBooleanInput(String prompt) {
        while (true) {
            String input = getStringInput(prompt).toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                System.out.println("Please enter 'y' for yes or 'n' for no.");
            }
        }
    }
}
