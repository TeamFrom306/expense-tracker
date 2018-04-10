package org.university.innopolis.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.university.innopolis.server.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account getById(int id);

    Account getByLogin(String login);

    Account getByToken(String token);

    Account getByIdAndLogin(int id, String login);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Account a SET a.token = NULL WHERE a.token = :token")
    void setTokenNull(@Param("token") String token);
}
