package org.university.innopolis.server;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"org.university.innopolis.server"})
@EnableJpaRepositories(basePackages = "org.university.innopolis.server.persistence")
public class ServicesConf {

//    @Autowired
//    void getAccR(AccountRepository accountRepository) {
//        return accountRepository;
//    }
}
