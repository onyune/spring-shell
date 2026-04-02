package com.nhnacademy.springshell.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.nhnacademy.springshell.domain.Account;
import com.nhnacademy.springshell.parser.DataParser;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private DataParser dataParser;

    @InjectMocks
    private AuthenticationService authenticationService;

    Account expect1 = new Account(1, "비밀번호", "이름");

    @Test
    public void account_success(){
        Account expect2 = new Account(2, "비번","네임");
        List<Account> expectList = List.of(expect1,expect2);
        when(dataParser.accounts()).thenReturn(expectList);

        Account actualAccount = authenticationService.account(1, "비밀번호");
        assertThat(actualAccount).isEqualTo(expect1);
    }

    @Test
    public void logout_success(){
        Account expect2 = new Account(2, "비번","네임");
        List<Account> expectList = List.of(expect1,expect2);
        when(dataParser.accounts()).thenReturn(expectList);

        authenticationService.account(expect1.getId(), expect1.getPassword());
        assertThat(authenticationService.getCurrentUser()).isEqualTo(expect1);
        authenticationService.logout();
        assertThat(authenticationService.getCurrentUser()).isEqualTo(null);

    }
}