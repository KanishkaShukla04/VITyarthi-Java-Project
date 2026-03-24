package com.ccrm.utils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for file operations using NIO.2.
 * Demonstrates modern Java I/O operations and file system utilities.
 */
public class FileUtils {
    
    /**
     * Creates a directory if it doesn't exist.
     * @param dirPath The path of the directory to create
     * @throws IOException if directory creation fails
     */
    public static void createDirectoryIfNotExists(String dirPath) throws IOException {
        Path path = Paths.get(dirPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    /**
     * Copies a file from source to destination.
     * @param sourcePath Source file path
     * @param destPath Destination file path
     * @throws IOException if copy operation fails
     */
    public static void copyFile(String sourcePath, String destPath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destPath);
        
        // Create parent directories if they don't exist
        Files.createDirectories(destination.getParent());
        
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Moves a file from source to destination.
     * @param sourcePath Source file path
     * @param destPath Destination file path
     * @throws IOException if move operation fails
     */
    public static void moveFile(String sourcePath, String destPath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destPath);
        
        // Create parent directories if they don't exist
        Files.createDirectories(destination.getParent());
        
        Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Deletes a file or directory recursively.
     * @param path The path to delete
     * @throws IOException if deletion fails
     */
    public static void deleteRecursively(String path) throws IOException {
        Path pathToDelete = Paths.get(path);
        if (Files.exists(pathToDelete)) {
            Files.walkFileTree(pathToDelete, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    /**
     * Checks if a file exists.
     * @param filePath The file path to check
     * @return true if file exists, false otherwise
     */
    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    /**
     * Gets the size of a file in bytes.
     * @param filePath The file path
     * @return File size in bytes
     * @throws IOException if file access fails
     */
    public static long getFileSize(String filePath) throws IOException {
        return Files.size(Paths.get(filePath));
    }

    /**
     * Gets the size of a directory recursively.
     * @param dirPath The directory path
     * @return Total size in bytes
     * @throws IOException if directory access fails
     */
    public static long getDirectorySize(String dirPath) throws IOException {
        Path path = Paths.get(dirPath);
        if (!Files.exists(path)) {
            return 0;
        }

        final long[] totalSize = {0};
        
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                totalSize[0] += attrs.size();
                return FileVisitResult.CONTINUE;
            }
        });

        return totalSize[0];
    }

    /**
     * Lists all files in a directory recursively.
     * @param dirPath The directory path
     * @return List of file paths
     * @throws IOException if directory access fails
     */
    public static List<String> listFilesRecursively(String dirPath) throws IOException {
        List<String> files = new ArrayList<>();
        Path path = Paths.get(dirPath);
        
        if (!Files.exists(path)) {
            return files;
        }

        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                files.add(file.toString());
                return FileVisitResult.CONTINUE;
            }
        });

        return files;
    }

    /**
     * Creates a timestamped backup directory.
     * @param baseDir The base directory for backups
     * @return The path of the created backup directory
     * @throws IOException if directory creation fails
     */
    public static String createTimestampedBackupDir(String baseDir) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String backupDir = baseDir + "/backup_" + timestamp;
        createDirectoryIfNotExists(backupDir);
        return backupDir;
    }

    /**
     * Formats file size in human-readable format.
     * @param bytes File size in bytes
     * @return Formatted size string
     */
    public static String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        if (bytes < 1024 * 1024 * 1024) return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
        return String.format("%.1f GB", bytes / (1024.0 * 1024.0 * 1024.0));
    }

    /**
     * Gets file extension from a file path.
     * @param filePath The file path
     * @return File extension (without dot) or empty string if no extension
     */
    public static String getFileExtension(String filePath) {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        int lastDotIndex = fileName.lastIndexOf('.');
        return lastDotIndex > 0 ? fileName.substring(lastDotIndex + 1) : "";
    }

    /**
     * Checks if a path is a directory.
     * @param path The path to check
     * @return true if it's a directory, false otherwise
     */
    public static boolean isDirectory(String path) {
        return Files.isDirectory(Paths.get(path));
    }

    /**
     * Checks if a path is a regular file.
     * @param path The path to check
     * @return true if it's a regular file, false otherwise
     */
    public static boolean isRegularFile(String path) {
        return Files.isRegularFile(Paths.get(path));
    }
}
