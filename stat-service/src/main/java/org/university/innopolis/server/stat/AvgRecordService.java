package org.university.innopolis.server.stat;

import java.util.Map;

public interface AvgRecordService {
    Map<String, Double> getAvgStat(int accountId);
}
