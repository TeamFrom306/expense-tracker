package org.university.innopolis.server.views;

import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.model.Record;

public class RecordView {
    private final Type type;

    public RecordView(Record record) {
        type = record.getType();
    }

    public Type getType() {
        return type;
    }
}
