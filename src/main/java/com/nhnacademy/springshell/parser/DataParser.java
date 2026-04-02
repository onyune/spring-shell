package com.nhnacademy.springshell.parser;

import com.nhnacademy.springshell.domain.Account;
import com.nhnacademy.springshell.domain.Price;
import java.util.List;

public interface DataParser {
    Price price(String city, String sector);
    List<Account> accounts();
    List<String> sectors(String city);
    List<String> cities();
}
