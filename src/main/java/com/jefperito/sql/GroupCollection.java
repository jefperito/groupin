package com.jefperito.sql;

import java.util.Collections;
import java.util.List;

public class GroupCollection {

    public String intoBetweens(String field, List<Long> numbers) {
        if (numbers.isEmpty()) {
            throw new IllegalStateException("Numbers cannot be empty.");
        }

        Collections.sort(numbers);
        SQLGroupData sqlGroupData = new GroupRearranger().groupNumbersInASQLContext(List.copyOf(numbers));

        return new StatementSQLBuilder().build(field, sqlGroupData);
    }
}
