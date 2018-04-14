package org.university.innopolis.server.services_realization.mappers;

import org.university.innopolis.server.model.Record;
import org.university.innopolis.server.views.RecordView;

public class RecordMapper {
    private RecordMapper() {
    }

    public static RecordView map(Record record) {
        return new RecordView(
                record.getDescription(),
                record.getAmount(),
                record.getCurrency(),
                record.getDate(),
                record.getType()
        );
    }
}
