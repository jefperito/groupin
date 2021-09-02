package com.jefperito.sql;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class StatementSQLBuilder {

    String getBetweenGroupsSQL(String field, List<List<Long>> sqlBetweenGroups) {
        String betweenGroupsSQL = null;
        if (!sqlBetweenGroups.isEmpty()) {
            betweenGroupsSQL = sqlBetweenGroups.stream()
                    .map(betweenGroupSQL -> getBetweenGroupSQL(field, betweenGroupSQL))
                    .collect(Collectors.joining(" OR "));
        }
        return betweenGroupsSQL;
    }

    String getInSQLGroup(String field, List<Long> sqlInGroup) {
        String inGroupSQL = null;
        if (!sqlInGroup.isEmpty()) {
            String inSQLGroup = sqlInGroup.stream().map(Object::toString).collect(Collectors.joining(", "));
            inGroupSQL = String.format("%s IN (%s)", field, inSQLGroup);
        }
        return inGroupSQL;
    }

    private String getBetweenGroupSQL(String field, Collection<Long> numbers) {
        List<Long> numberAsList = numbers.stream().collect(Collectors.toList());
        Collections.sort(numberAsList);
        Long firstNumber = getFirstNumber(numberAsList);
        Long lastNumber = getLastNumber(numberAsList);

        return String.format("%s BETWEEN %s AND %s", field, firstNumber, lastNumber);
    }

    String build(String field, SQLGroupData sqlGroupData) {
        String betweenSQLGroups = getBetweenGroupsSQL(field, sqlGroupData.getBetweenGroups());
        String inGroupSQL = getInSQLGroup(field, sqlGroupData.getInGroup());

        return build(betweenSQLGroups, inGroupSQL);
    }

    String build(String betweenSQLGroups, String inGroupSQL) {
        if (betweenSQLGroups != null && inGroupSQL != null) {
            return String.format("%s OR %s", betweenSQLGroups, inGroupSQL);
        }
        if (betweenSQLGroups != null && inGroupSQL == null) {
            return betweenSQLGroups;
        }

        return inGroupSQL;
    }

    private static Long getLastNumber(List<Long> numbers) {
        return numbers.get(numbers.size() - 1);
    }

    private static Long getFirstNumber(List<Long> numbers) {
        return numbers.get(0);
    }
}
