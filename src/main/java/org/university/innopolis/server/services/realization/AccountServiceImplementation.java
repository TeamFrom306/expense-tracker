package org.university.innopolis.server.services.realization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.university.innopolis.server.model.Account;
import org.university.innopolis.server.model.Holder;
import org.university.innopolis.server.persistence.AccountRepository;
import org.university.innopolis.server.persistence.HolderRepository;
import org.university.innopolis.server.services.AccountService;
import org.university.innopolis.server.services.realization.mappers.AccountMapper;
import org.university.innopolis.server.views.AccountView;

@Service
public class AccountServiceImplementation implements AccountService {

    private AccountRepository accountRepository;
    private HolderRepository holderRepository;

    @Autowired
    public AccountServiceImplementation(AccountRepository accountRepository, HolderRepository holderRepository) {
        this.accountRepository = accountRepository;
        this.holderRepository = holderRepository;
    }

    @Override
    public AccountView adjustBalance(double newBalance, int holderId, int accountId) {
        Account account = accountRepository.getById(accountId);
        account.setBalance(newBalance);
        return AccountMapper.map(accountRepository.save(account));
    }

    @Override
    public AccountView createAccount(String name, int holderId) {
        Account account = new Account(name);
        Holder holder = holderRepository.getById(holderId);
        account.setHolder(holder);
        account = accountRepository.save(account);

        return AccountMapper.map(account);
    }
}
