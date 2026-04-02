package com.nhnacademy.springshell.service;

import com.nhnacademy.springshell.domain.Account;
import com.nhnacademy.springshell.exception.UserNotFoundException;
import com.nhnacademy.springshell.parser.DataParser;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    @Getter
    private Account currentUser;
    private final DataParser parser;

    public Account account(long id, String password) {
        List<Account> userList = parser.accounts();

        for (Account account : userList) {
            if (account.getId() == id && account.getPassword().equals(password)) {
                currentUser = account;
                return account;
            }
        }
        throw new UserNotFoundException();
    }


    public void logout() {
        currentUser=null;
    }

}
