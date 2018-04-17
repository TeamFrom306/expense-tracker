package org.university.innopolis.server.services.realization;

import org.springframework.stereotype.Service;
import org.university.innopolis.server.services.realization.calculators.DayExpensesCalculator;
import org.university.innopolis.server.services.realization.calculators.DayIncomesCalculator;
import org.university.innopolis.server.services.realization.calculators.WeekExpensesCalculator;
import org.university.innopolis.server.services.realization.calculators.WeekIncomesCalculator;
import org.university.innopolis.server.views.RecordView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
