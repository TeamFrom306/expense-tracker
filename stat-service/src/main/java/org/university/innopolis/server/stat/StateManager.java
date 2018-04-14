package org.university.innopolis.server.stat;

import org.springframework.stereotype.Service;
import org.university.innopolis.server.stat.calculators.*;
import org.university.innopolis.server.views.RecordView;

import java.util.*;

@Service
class StateManager {
    private List<RecordsCalculator> calculators = new ArrayList<>();

    StateManager() {
        calculators.add(new DayExpensesCalculator());
        calculators.add(new DayIncomesCalculator());
        calculators.add(new WeekExpensesCalculator());
        calculators.add(new WeekIncomesCalculator());
    }

    public void appendRecord(int accountId, RecordView record) {
        for (RecordsCalculator calculator : calculators) {
            if (record.getType() == calculator.getType())
                calculator.registerRecord(accountId, record);
        }
    }

    public Map<String, Double> getStats(int accountId) {
        HashMap<String, Double> res = new HashMap<>();
        for (RecordsCalculator calculator : calculators) {
            calculator.fillMap(accountId, res);
        }
        return res;
    }
}
