package org.university.innopolis.server.services.realization.calculators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.university.innopolis.server.services.realization.RecordsCalculator;
import org.university.innopolis.server.views.RecordView;

import java.io.IOException;
import java.util.*;

public abstract class RecordsCalculatorBase implements RecordsCalculator {
    private static Logger logger = LoggerFactory.getLogger(RecordsCalculatorBase.class);
    private Map<Integer, Double> total = new HashMap<>();
    private Map<Integer, List<RecordView>> queue = new HashMap<>();

    @Override
    public void registerRecord(int holderId, RecordView record) {
        queue.computeIfAbsent(holderId, k -> new ArrayList<>());
        total.putIfAbsent(holderId, 0d);

        long startTime = computeTime();
        if (record.getDate().getTime() / 1000 < startTime)
            return;

        List<RecordView> recordList = queue.get(holderId);
        recordList.add(record);
        total.compute(holderId, (k, v) -> v + record.getAmount());
    }

    @Override
    public String exportToJson() {
        ObjectMapper mapper = new ObjectMapper();
        CalculatorState state = new CalculatorState(total, queue);
        try {
            return mapper.writeValueAsString(state);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void importFromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            CalculatorState state = mapper.readValue(json, CalculatorState.class);
            this.queue = state.getQueue();
            this.total = state.getTotal();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    double getAverage(int holderId) {
        List<RecordView> list = filterByTime(holderId);

        if (list == null)
            return 0;

        return total.get(holderId) / (list.isEmpty() ? 1 : list.size());
    }

    private List<RecordView> filterByTime(int holderId) {
        List<RecordView> list = queue.get(holderId);
        if (list == null)
            return null;
        long startTime = computeTime();
        int i = 0;
        while (i < list.size()) {
            RecordView record = list.get(i);
            if (record.getDate().getTime() / 1000 < startTime) {
                total.compute(holderId, (k, v) -> v - record.getAmount());
                list.remove(i);
            } else {
                i++;
            }
        }
        return list;
    }

    private long computeTime() {
        long time = new Date().getTime() / 1000;
        return time - (time % getQuantificationTime());
    }

    protected abstract long getQuantificationTime();

}
