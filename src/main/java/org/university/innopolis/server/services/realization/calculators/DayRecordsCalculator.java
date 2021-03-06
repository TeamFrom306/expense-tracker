package org.university.innopolis.server.services.realization.calculators;

abstract class DayRecordsCalculator extends RecordsCalculatorBase {
    private static final long TIME_QUANTIFICATION = 84600; // 3600 * 24

    @Override
    protected long getQuantificationTime() {
        return TIME_QUANTIFICATION;
    }
}
