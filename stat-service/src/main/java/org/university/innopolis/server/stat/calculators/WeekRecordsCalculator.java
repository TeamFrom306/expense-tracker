package org.university.innopolis.server.stat.calculators;

public abstract class WeekRecordsCalculator extends RecordsCalculatorBase {
    private static final long TIME_QUANTIFICATION = 592200; // 3600 * 24 * 7

    @Override
    protected long getQuantificationTime() {
        return TIME_QUANTIFICATION;
    }
}
