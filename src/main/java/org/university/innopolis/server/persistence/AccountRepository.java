package org.university.innopolis.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.university.innopolis.server.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account getById(int id);
}
