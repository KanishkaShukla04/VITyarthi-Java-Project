package com.ccrm.interfaces;

import java.util.List;

/**
 * Interface for objects that can be persisted to and loaded from files.
 * Demonstrates interface usage for defining contracts.
 */
public interface Persistable<T> {
    /**
     * Save the object to a file.
     * @param filePath The path where to save the file
     * @throws Exception if saving fails
     */
    void saveToFile(String filePath) throws Exception;

    /**
     * Load objects from a file.
     * @param filePath The path of the file to load from
     * @return List of loaded objects
     * @throws Exception if loading fails
     */
    List<T> loadFromFile(String filePath) throws Exception;

    /**
     * Export objects to CSV format.
     * @param filePath The path where to save the CSV file
     * @throws Exception if export fails
     */
    void exportToCSV(String filePath) throws Exception;

    /**
     * Import objects from CSV format.
     * @param filePath The path of the CSV file to import from
     * @return List of imported objects
     * @throws Exception if import fails
     */
    List<T> importFromCSV(String filePath) throws Exception;
}
