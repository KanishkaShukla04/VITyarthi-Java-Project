package com.ccrm.interfaces;

import java.util.List;

/**
 * Interface for objects that support search functionality.
 * Demonstrates generic interface usage.
 * @param <T> The type of objects being searched
 */
public interface Searchable<T> {
    /**
     * Search for objects by a specific field.
     * @param field The field to search by
     * @param value The value to search for
     * @return List of matching objects
     */
    List<T> searchByField(String field, String value);

    /**
     * Search for objects by multiple criteria.
     * @param criteria The search criteria
     * @return List of matching objects
     */
    List<T> searchByCriteria(SearchCriteria criteria);

    /**
     * Filter objects based on a predicate.
     * @param predicate The filtering predicate
     * @return List of filtered objects
     */
    List<T> filter(Predicate<T> predicate);

    /**
     * Search criteria class for complex searches.
     */
    class SearchCriteria {
        private String field;
        private String value;
        private SearchOperator operator;

        public SearchCriteria(String field, String value, SearchOperator operator) {
            this.field = field;
            this.value = value;
            this.operator = operator;
        }

        public String getField() { return field; }
        public String getValue() { return value; }
        public SearchOperator getOperator() { return operator; }
    }

    /**
     * Search operators for different types of searches.
     */
    enum SearchOperator {
        EQUALS, CONTAINS, STARTS_WITH, ENDS_WITH, GREATER_THAN, LESS_THAN
    }

    /**
     * Functional interface for predicates.
     */
    @FunctionalInterface
    interface Predicate<T> {
        boolean test(T item);
    }
}
