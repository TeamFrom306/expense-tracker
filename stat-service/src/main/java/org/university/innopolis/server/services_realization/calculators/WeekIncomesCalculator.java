package org.university.innopolis.server.services_realization.calculators;

import org.university.innopolis.server.common.Type;

import java.util.Map;

public class WeekIncomesCalculator extends WeekRecordsCalculator {
    @Override
    public void fillMap(int accountId, Map<String, Double> res) {
        res.put("weekAvgIncomes", getAverage(accountId));
    }

    @Override
    public Type getType() {
        return Type.INCOME;
    }
}
