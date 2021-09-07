package com.jefperito.sql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupCollectionTest {

    private GroupCollection groupCollection;

    @BeforeEach
    public void setUpTest() {
        groupCollection = new GroupCollection();
    }

    @Test
    public void shouldThrowsAExceptionCaseBeAnEmptyCollection() {
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> new GroupCollection().intoBetweens("id", new ArrayList<>()),
                "Numbers cannot be empty."
        );
    }

    @Test
    public void shouldReturnInStatementWithOneLongWhetherNumbersCollectionHasOneNumber() {
        Assertions.assertEquals("id IN (1)", groupCollection.intoBetweens("id", Arrays.asList(1L)));
    }

    @Test
    public void shouldReturnBetweenStatementWithLongsWhetherNumbersCollectionHasMoreThanOneNumberInARow() {
        Assertions.assertEquals("id BETWEEN 1 AND 3", groupCollection.intoBetweens("id", Arrays.asList(1L, 2L, 3L)));
    }

    @Test
    public void shouldReturnBetweenStatementWithLongsWhetherNumbersCollectionHasMoreThanOneNumberInARowEvenTheElementsAreNotSorted() {
        Assertions.assertEquals("id BETWEEN 1 AND 3", groupCollection.intoBetweens("id", Arrays.asList(3L, 1L, 2L)));
    }

    @Test
    public void shouldReturnsBetweenStatementAndInStatementWithLongWhetherNumbersCollectionHasMoreThanOneNumberInARowButHaveGapToTheNextNumber() {
        Assertions.assertEquals("id BETWEEN 1 AND 3 OR id IN (5)", new GroupCollection().intoBetweens("id", Arrays.asList(1L, 2L, 3L, 5L)));
    }

    @Test
    public void shouldReturnsBetweenStatementAndInStatementWithLotsOfLongsWithOrWithoutGapsAmongThem() {
        Assertions.assertEquals("id BETWEEN 3 AND 6 OR id BETWEEN 11 AND 12 OR id IN (1, 9, 17)", new GroupCollection().intoBetweens("id", Arrays.asList(3L, 1L, 6L, 4L, 9L, 12L, 11L, 17L, 5L)));
    }
}
