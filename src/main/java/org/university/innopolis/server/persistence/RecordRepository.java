package org.university.innopolis.server.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.model.Record;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    Record getById(int id);

    List<Record> getByType(Type type);

    List<Record> getRecordsByHolder_Id(int holder_id);

    List<Record> getRecordsByHolder_IdOrderByDateDesc(int holder_id, Pageable pageable);
}
