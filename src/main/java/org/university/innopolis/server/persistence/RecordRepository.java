package org.university.innopolis.server.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.model.Record;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    Record getById(int id);

    List<Record> getByType(Type type);

    List<Record> getRecordsByAccountHolderIdOrderByDateDesc(int holderId);

    List<Record> getRecordsByAccountHolderIdOrderByDateDesc(
            int holderId,
            Pageable pageable);

    List<Record> getRecordsByAccountIdAndAccountHolderIdOrderByDateDesc(
            int accountId,
            int accountHolderId,
            Pageable pageable);
}
