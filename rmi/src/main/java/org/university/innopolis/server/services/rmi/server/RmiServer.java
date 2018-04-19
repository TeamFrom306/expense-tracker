package org.university.innopolis.server.services.rmi.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.stereotype.Component;
import org.university.innopolis.server.services.*;
import org.university.innopolis.server.services.realization.AccountServiceImplementation;

import java.rmi.Remote;

@Component
@ComponentScan(basePackages = {"org.university.innopolis.server"})
public class RmiServer implements Remote {

    private RmiServiceExporter getExporter(Object realization, Class<?> inter) {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(inter);
        exporter.setService(realization);
        exporter.setServiceName(inter.getSimpleName());
        exporter.setRegistryPort(1099);
        return exporter;
    }

    @Bean(name = "AccountService")
    @Autowired
    RmiServiceExporter exporter1(AccountServiceImplementation accountServiceImplementation) {
        return getExporter(accountServiceImplementation, AccountService.class);
    }

    @Bean(name = "GetRecordService")
    @Autowired
    RmiServiceExporter exporter2(GetRecordService recordService) {
        return getExporter(recordService, GetRecordService.class);
    }

    @Bean(name = "AddRecordService")
    @Autowired
    RmiServiceExporter exporter3(AddRecordService proxyAddRecordService) {
        return getExporter(proxyAddRecordService, AddRecordService.class);
    }

    @Bean(name = "AuthenticationService")
    @Autowired
    RmiServiceExporter exporter4(AuthenticationService authenticationService) {
        return getExporter(authenticationService, AuthenticationService.class);
    }

    @Bean(name = "AvgRecordService")
    @Autowired
    RmiServiceExporter exporter5(AvgRecordService proxyAddRecordService) {
        return getExporter(proxyAddRecordService, AvgRecordService.class);
    }

    @Bean(name = "TokenService")
    @Autowired
    RmiServiceExporter exporter6(TokenService tokenService) {
        return getExporter(tokenService, TokenService.class);
    }

    @Bean(name = "StateExporter")
    @Autowired
    RmiServiceExporter exporter7(StateService stateService) {
        return getExporter(stateService, StateService.class);
    }
}
