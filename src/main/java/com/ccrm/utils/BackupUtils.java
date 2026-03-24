package com.ccrm.utils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for backup operations using recursive directory traversal.
 * Demonstrates recursion and file system operations.
 */
public class BackupUtils {
    
    /**
     * Creates a backup of all data files to a timestamped directory.
     * @param dataDir The directory containing data files
     * @param backupBaseDir The base directory for backups
     * @return The path of the created backup directory
     * @throws IOException if backup creation fails
     */
    public static String createBackup(String dataDir, String backupBaseDir) throws IOException {
        String backupDir = FileUtils.createTimestampedBackupDir(backupBaseDir);
        
        // Copy all files from data directory to backup directory
        copyDirectoryRecursively(dataDir, backupDir);
        
        return backupDir;
    }

    /**
     * Recursively copies a directory and all its contents.
     * @param sourceDir Source directory path
     * @param targetDir Target directory path
     * @throws IOException if copy operation fails
     */
    public static void copyDirectoryRecursively(String sourceDir, String targetDir) throws IOException {
        Path source = Paths.get(sourceDir);
        Path target = Paths.get(targetDir);
        
        if (!Files.exists(source)) {
            throw new IOException("Source directory does not exist: " + sourceDir);
        }
        
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetDir = target.resolve(source.relativize(dir));
                Files.createDirectories(targetDir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path targetFile = target.resolve(source.relativize(file));
                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Recursively calculates the total size of a directory.
     * @param dirPath The directory path
     * @return Total size in bytes
     * @throws IOException if directory access fails
     */
    public static long calculateDirectorySizeRecursively(String dirPath) throws IOException {
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
     * Recursively lists all files in a directory with their sizes.
     * @param dirPath The directory path
     * @return List of file information
     * @throws IOException if directory access fails
     */
    public static List<FileInfo> listFilesRecursivelyWithSize(String dirPath) throws IOException {
        List<FileInfo> files = new ArrayList<>();
        Path path = Paths.get(dirPath);
        
        if (!Files.exists(path)) {
            return files;
        }

        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                files.add(new FileInfo(file.toString(), attrs.size(), attrs.creationTime().toInstant()));
                return FileVisitResult.CONTINUE;
            }
        });

        return files;
    }

    /**
     * Recursively counts files and directories in a directory.
     * @param dirPath The directory path
     * @return File count information
     * @throws IOException if directory access fails
     */
    public static FileCountInfo countFilesRecursively(String dirPath) throws IOException {
        Path path = Paths.get(dirPath);
        if (!Files.exists(path)) {
            return new FileCountInfo(0, 0, 0);
        }

        final int[] fileCount = {0};
        final int[] dirCount = {0};
        final long[] totalSize = {0};
        
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (!dir.equals(path)) { // Don't count the root directory
                    dirCount[0]++;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                fileCount[0]++;
                totalSize[0] += attrs.size();
                return FileVisitResult.CONTINUE;
            }
        });

        return new FileCountInfo(fileCount[0], dirCount[0], totalSize[0]);
    }

    /**
     * Recursively finds files matching a pattern.
     * @param dirPath The directory path to search
     * @param pattern The file pattern to match (e.g., "*.csv", "*.txt")
     * @return List of matching file paths
     * @throws IOException if directory access fails
     */
    public static List<String> findFilesRecursively(String dirPath, String pattern) throws IOException {
        List<String> matchingFiles = new ArrayList<>();
        Path path = Paths.get(dirPath);
        
        if (!Files.exists(path)) {
            return matchingFiles;
        }

        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (matcher.matches(file.getFileName())) {
                    matchingFiles.add(file.toString());
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return matchingFiles;
    }

    /**
     * Recursively deletes old backup directories based on age.
     * @param backupDir The backup directory
     * @param maxAgeInDays Maximum age in days for backups to keep
     * @return Number of directories deleted
     * @throws IOException if deletion fails
     */
    public static int cleanupOldBackups(String backupDir, int maxAgeInDays) throws IOException {
        Path backupPath = Paths.get(backupDir);
        if (!Files.exists(backupPath)) {
            return 0;
        }

        final int[] deletedCount = {0};
        long maxAgeMillis = maxAgeInDays * 24L * 60L * 60L * 1000L;
        long cutoffTime = System.currentTimeMillis() - maxAgeMillis;

        Files.walkFileTree(backupPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (!dir.equals(backupPath) && dir.getFileName().toString().startsWith("backup_")) {
                    if (attrs.creationTime().toMillis() < cutoffTime) {
                        FileUtils.deleteRecursively(dir.toString());
                        deletedCount[0]++;
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return deletedCount[0];
    }

    /**
     * Class representing file information.
     */
    public static class FileInfo {
        private final String path;
        private final long size;
        private final java.time.Instant creationTime;

        public FileInfo(String path, long size, java.time.Instant creationTime) {
            this.path = path;
            this.size = size;
            this.creationTime = creationTime;
        }

        public String getPath() { return path; }
        public long getSize() { return size; }
        public java.time.Instant getCreationTime() { return creationTime; }

        @Override
        public String toString() {
            return String.format("%s (%s)", path, FileUtils.formatFileSize(size));
        }
    }

    /**
     * Class representing file count information.
     */
    public static class FileCountInfo {
        private final int fileCount;
        private final int directoryCount;
        private final long totalSize;

        public FileCountInfo(int fileCount, int directoryCount, long totalSize) {
            this.fileCount = fileCount;
            this.directoryCount = directoryCount;
            this.totalSize = totalSize;
        }

        public int getFileCount() { return fileCount; }
        public int getDirectoryCount() { return directoryCount; }
        public long getTotalSize() { return totalSize; }

        @Override
        public String toString() {
            return String.format("Files: %d, Directories: %d, Total Size: %s", 
                               fileCount, directoryCount, FileUtils.formatFileSize(totalSize));
        }
    }
}
