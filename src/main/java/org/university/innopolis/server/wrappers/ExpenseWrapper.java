package org.university.innopolis.server.wrappers;

import org.university.innopolis.server.common.Type;

public class ExpenseWrapper extends RecordWrapper {
    @Override
    public Type getType() {
        return Type.EXPENSE;
    }
}
