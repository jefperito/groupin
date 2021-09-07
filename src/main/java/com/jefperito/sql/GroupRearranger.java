package com.jefperito.sql;

import java.util.ArrayList;
import java.util.List;

public class GroupRearranger {

    private int index;

    GroupRearranger() {
        index = 0;
    }

    SQLGroupData groupNumbersInASQLContext(List<Long> numbers) {
        List<List<Long>> groupOfNumberInASQLContext = new ArrayList<>();
        List<Long> numbersWithoutAGroup = new ArrayList<>();

        for (index = 0; index < numbers.size(); index++) {
            List<Long> nextGroup = getNextGroup(numbers);
            if (numberIsNotInARow(nextGroup)) {
                numbersWithoutAGroup.addAll(nextGroup);
                continue;
            }
            groupOfNumberInASQLContext.add(nextGroup);
        }
        index = 0;
        return new SQLGroupData(groupOfNumberInASQLContext, numbersWithoutAGroup);
    }

    private boolean numberIsNotInARow(List<Long> group) {
        return group.size() == 1;
    }

    private List<Long> getNextGroup(List<Long> numbers) {
        List<Long> longs = List.copyOf(numbers);
        List<Long> group = new ArrayList<>();
        for (int indexNextGroup = index; indexNextGroup < longs.size(); indexNextGroup++) {
            long nextNumber = longs.get(indexNextGroup);
            if (group.isEmpty()) {
                group.add(nextNumber);
                continue;
            }

            if (hasGap(group, nextNumber)) {
                return group;
            }
            index = indexNextGroup;
            group.add(nextNumber);
        }
        return group;
    }

    private boolean hasGap(List<Long> group, long nextNumber) {
        Long lastNumberOnGroup = group.get(group.size() - 1);
        lastNumberOnGroup++;

        return !(lastNumberOnGroup == nextNumber);
    }
}
