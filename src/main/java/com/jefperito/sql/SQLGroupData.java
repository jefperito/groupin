package com.jefperito.sql;

import java.util.List;

class SQLGroupData {
    private List<List<Long>> betweenGroups;
    private List<Long> inGroup;

    SQLGroupData(List<List<Long>> betweenGroups, List<Long> inGroup) {
        this.betweenGroups = betweenGroups;
        this.inGroup = inGroup;
    }

    List<List<Long>> getBetweenGroups() {
        return betweenGroups;
    }

    List<Long> getInGroup() {
        return inGroup;
    }
}
