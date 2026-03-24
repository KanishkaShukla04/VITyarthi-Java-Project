@echo off
echo Building Campus Course & Records Manager (CCRM)...

REM Create output directory
if not exist "bin" mkdir bin

REM Compile all Java files
javac -d bin -cp src/main/java src/main/java/com/ccrm/*.java src/main/java/com/ccrm/**/*.java

if %ERRORLEVEL% EQU 0 (
    echo Build successful!
    echo.
    echo To run the application:
    echo java -cp bin com.ccrm.CampusCourseRecordsManager
) else (
    echo Build failed! Please check for compilation errors.
)

pause
