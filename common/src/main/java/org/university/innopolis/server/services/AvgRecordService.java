package org.university.innopolis.server.services;

import java.util.Map;

public interface AvgRecordService {
    Map<String, Double> getAvgStat(int accountId);
}
