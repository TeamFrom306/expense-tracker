package org.university.innopolis.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.model.Record;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    Record getById(int id);

    Record[] getByType(Type type);
}
