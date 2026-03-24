# Quick Start Guide - Campus Course & Records Manager

## 🚀 Getting Started in 5 Minutes

### Step 1: Build the Application
```bash
# Windows
build.bat

# Or manually compile
javac -d bin -cp src/main/java src/main/java/com/ccrm/*.java src/main/java/com/ccrm/**/*.java
```

### Step 2: Run the Application
```bash
# Windows
run.bat

# Or manually run
java -cp bin com.ccrm.CampusCourseRecordsManager
```

### Step 3: Load Sample Data (Optional)
1. Go to **File Operations** → **Import Students from CSV**
2. Enter: `sample_data/students.csv`
3. Repeat for courses: `sample_data/courses.csv`
4. Repeat for enrollments: `sample_data/enrollments.csv`

### Step 4: Explore the System
- **Student Management**: View imported students, generate transcripts
- **Course Management**: Browse courses by department
- **Enrollment & Grading**: View student enrollments and grades
- **System Reports**: Check GPA distribution and statistics
- **Backup Operations**: Create a backup of your data

## 🎯 Key Features to Try

### 1. Student Transcript Generation
- Go to **Student Management** → **Generate Student Transcript**
- Enter student ID: `STU001`
- Choose to include inactive courses
- View the formatted transcript

### 2. Course Search
- Go to **Course Management** → **Search Courses by Department**
- Select **Computer Science**
- View all CS courses

### 3. GPA Calculation
- Go to **Enrollment & Grading** → **Calculate Student GPA**
- Enter student ID: `STU002`
- View calculated GPA

### 4. System Statistics
- Go to **System Reports** → **System Statistics**
- View counts of students, courses, and enrollments

### 5. Backup Creation
- Go to **Backup Operations** → **Create Backup**
- Check the backup directory for timestamped folders

## 📊 Sample Data Overview

The sample data includes:
- **5 Students** with realistic information
- **8 Courses** across different departments
- **12 Enrollments** with completed grades
- **GPA calculations** already computed

## 🔧 Troubleshooting

### Common Issues

1. **"Student not found"**
   - Make sure you've imported the sample data
   - Check that student IDs match exactly (case-sensitive)

2. **"Course not found"**
   - Ensure courses are imported from sample data
   - Verify course IDs are correct

3. **Compilation errors**
   - Make sure you're using Java 8 or higher
   - Check that all source files are present

4. **File not found errors**
   - Ensure you're running from the project root directory
   - Check that sample_data folder exists

### Getting Help

- Check the main README.md for detailed documentation
- Review the code comments for implementation details
- All technical concepts are documented with examples

## 🎓 Learning Objectives

This project demonstrates:
- **Object-Oriented Programming**: Inheritance, encapsulation, polymorphism
- **Design Patterns**: Singleton, Builder patterns
- **Modern Java**: Streams, NIO.2, Date/Time API
- **File Operations**: CSV import/export, backup systems
- **Exception Handling**: Custom exceptions and error management
- **Business Logic**: Credit limits, GPA calculation, enrollment rules

## 📈 Next Steps

1. **Explore the Code**: Read through the source files to understand implementation
2. **Modify Data**: Add your own students and courses
3. **Extend Features**: Try adding new functionality
4. **Study Patterns**: Understand how design patterns are applied
5. **Review Documentation**: Check the comprehensive README.md

Enjoy exploring the Campus Course & Records Manager! 🎉
