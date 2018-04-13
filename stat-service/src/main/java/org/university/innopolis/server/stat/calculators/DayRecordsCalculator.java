package org.university.innopolis.server.stat.calculators;

abstract class DayRecordsCalculator extends RecordsCalculator {
    private static final long TIME_QUANTIFICATION = 84600; // 3600 * 24

    @Override
    protected long getQuantificationTime() {
        return TIME_QUANTIFICATION;
    }
}
