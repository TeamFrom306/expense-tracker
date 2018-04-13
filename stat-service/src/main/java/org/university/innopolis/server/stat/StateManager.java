package org.university.innopolis.server.stat;

import org.springframework.stereotype.Service;
import org.university.innopolis.server.views.RecordView;

import java.util.*;

@Service
class StateManager {
    private List<RecordsCalculator> calculators = new ArrayList<>();

    StateManager() {
        calculators.add(new DayRecordsCalculator());
    }

    public void appendRecord(int accountId, RecordView record) {
        for (RecordsCalculator calculator : calculators) {
            calculator.registerRecord(accountId, record);
        }
    }
}
