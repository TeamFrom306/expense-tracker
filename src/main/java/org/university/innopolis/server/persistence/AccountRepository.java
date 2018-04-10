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

    @Transactional
    @Modifying
    @Query("UPDATE Account ac SET ac.balance = :amount WHERE ac.id = :id")
    void updateBalanceById(@Param("id") int id, @Param("amount") double amount);
}
