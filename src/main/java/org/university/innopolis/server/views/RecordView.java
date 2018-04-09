package org.university.innopolis.server.views;

import org.university.innopolis.server.model.Record;
import org.university.innopolis.server.model.Type;

public class RecordView {
    private final Type type;

    public RecordView(Record record) {
        type = record.getType();
    }

    public Type getType() {
        return type;
    }
}
