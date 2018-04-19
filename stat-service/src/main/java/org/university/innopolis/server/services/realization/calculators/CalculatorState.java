package org.university.innopolis.server.services.realization.calculators;

import org.university.innopolis.server.views.RecordView;

import java.util.List;
import java.util.Map;

public class CalculatorState {
    private Map<Integer, Double> total;
    private Map<Integer, List<RecordView>> queue;

    public CalculatorState(){}

    public CalculatorState(Map<Integer, Double> total, Map<Integer, List<RecordView>> queue) {
        this.total = total;
        this.queue = queue;
    }

    public Map<Integer, Double> getTotal() {
        return total;
    }

    public Map<Integer, List<RecordView>> getQueue() {
        return queue;
    }
}
