package org.university.innopolis.server.rmi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.stereotype.Component;
import org.university.innopolis.server.services.*;

@Component
@ComponentScan(basePackages = {"org.university.innopolis.server"})
public class RmiServices {

    private Object getService(Class<?> cls) {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/" + cls.getSimpleName());
        rmiProxyFactoryBean.setServiceInterface(cls);
        rmiProxyFactoryBean.afterPropertiesSet();
        return rmiProxyFactoryBean.getObject();
    }

    @Bean("accountServiceRmi")
    AccountService getAccountService() {
        return (AccountService) getService(AccountService.class);
    }

    @Bean("authServiceRmi")
    AuthenticationService getAuthenticationService() {
        return (AuthenticationService) getService(AuthenticationService.class);
    }

    @Bean("avgRecordService")
    AvgRecordService getAvgRecordService() {
        return (AvgRecordService) getService(AvgRecordService.class);
    }

    @Bean("addRecordService")
    AddRecordService getAddRecordService() {
        return (AddRecordService) getService(AddRecordService.class);
    }

    @Bean("getRecordService")
    GetRecordService getRecordService() {
        return (GetRecordService) getService(GetRecordService.class);
    }

    @Bean("getTokenService")
    TokenService getTokenService() {
        return (TokenService) getService(TokenService.class);
    }
}
