package org.university.innopolis.server.services.realization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.innopolis.server.services.StateService;
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
class CalculatorManager {
    private List<RecordsCalculator> calculators = new ArrayList<>();
    private static Logger logger = LoggerFactory.getLogger(CalculatorManager.class);

    @Autowired
    CalculatorManager(StateService stateService) {
        calculators.add(new DayExpensesCalculator());
        calculators.add(new DayIncomesCalculator());
        calculators.add(new WeekExpensesCalculator());
        calculators.add(new WeekIncomesCalculator());

        for (RecordsCalculator calculator : calculators) {
            stateService.subscribe(calculator);
        }
    }

    public void appendRecord(int holderId, RecordView record) {
        for (RecordsCalculator calculator : calculators) {
            if (record.getType() == calculator.getType())
                calculator.registerRecord(holderId, record);
        }
    }

    public Map<String, Double> getStats(int holderId) {
        HashMap<String, Double> res = new HashMap<>();
        for (RecordsCalculator calculator : calculators) {
            calculator.fillMap(holderId, res);
        }
        return res;
    }
}
