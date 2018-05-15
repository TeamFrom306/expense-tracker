package org.university.innopolis.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.university.innopolis.server.model.Holder;

public interface HolderRepository extends JpaRepository<Holder, Integer> {
    Holder getById(int id);

    Holder getByLogin(String login);

    Holder getByToken(String token);

    Holder getByIdAndLogin(int id, String login);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Holder a SET a.token = NULL WHERE a.token = :token")
    void setTokenNull(@Param("token") String token);
}
