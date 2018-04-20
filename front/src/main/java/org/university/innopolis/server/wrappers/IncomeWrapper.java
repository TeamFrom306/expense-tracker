package org.university.innopolis.server.wrappers;

import org.university.innopolis.server.common.Type;

public class IncomeWrapper extends RecordWrapper {
    @Override
    public Type getType() {
        return Type.INCOME;
    }
}
