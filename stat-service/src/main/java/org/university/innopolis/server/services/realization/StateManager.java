package org.university.innopolis.server.services.realization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.university.innopolis.server.services.realization.calculators.DayExpensesCalculator;
import org.university.innopolis.server.services.realization.calculators.DayIncomesCalculator;
import org.university.innopolis.server.services.realization.calculators.WeekExpensesCalculator;
import org.university.innopolis.server.services.realization.calculators.WeekIncomesCalculator;
import org.university.innopolis.server.views.RecordView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Service
class StateManager {
    private List<RecordsCalculator> calculators = new ArrayList<>();
    private static Logger logger = LoggerFactory.getLogger(StateManager.class);

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

    public void exportState(String path) {
        try (FileWriter writer = new FileWriter(path)) {
            for (RecordsCalculator calculator : calculators) {
                String s = calculator.exportToJson();
                writer
                        .append(calculator.getClass().getSimpleName())
                        .append("\n")
                        .append(s)
                        .append("\n");
            }
            logger.info("Export to {} done", path);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void importState(String path) {
        try (Scanner reader = new Scanner(new File(path))) {
            for (RecordsCalculator calculator : calculators) {
                String className = reader.next();
                if (calculator.getClass().getSimpleName().equals(className)) {
                    String json = reader.next();
                    calculator.importFromJson(json);
                }
            }
            logger.info("Import from {} done", path);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
    }
}
