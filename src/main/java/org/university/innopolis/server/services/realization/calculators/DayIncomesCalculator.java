package org.university.innopolis.server.services.realization.calculators;

import org.university.innopolis.server.common.Type;

import java.util.Map;

public class DayIncomesCalculator extends DayRecordsCalculator {
    @Override
    public void fillMap(int holderId, Map<String, Double> res) {
        res.put("dayAvgIncomes", getAverage(holderId));
    }

    @Override
    public Type getType() {
        return Type.INCOME;
    }
}
