package com.nhnacademy.springshell.command;

import com.nhnacademy.springshell.domain.Account;
import com.nhnacademy.springshell.service.AuthenticationService;
import com.nhnacademy.springshell.service.PriceService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class MyCommands {
    private final AuthenticationService authenticationService;
    private final PriceService priceService;

    @ShellMethod("login")
    public String login(long id, String password){
        if(Objects.isNull(password) || password.isBlank()){
           throw new IllegalArgumentException("password가 빈칸이거나 null입니다.");
        }
        return authenticationService.account(id, password).toString();
    }

    @ShellMethod("logout")
    public String logout(){
        Account currentUser = authenticationService.getCurrentUser();
        if(Objects.isNull(currentUser)){
            return "현재 로그인된 계정이 없습니다."; //현재 로그인된 계정이 없음
        }

        authenticationService.logout();
        return "goodBye";
    }

    @ShellMethod("current-user")
    public String currentUser(){
        Account currentUser = authenticationService.getCurrentUser();
        if(Objects.isNull(currentUser)){
            return " ";
        }
        return currentUser.toString();
    }

    @ShellMethod("city")
    public String city(){
        return priceService.cities().toString();
    }
    @ShellMethod("price")
    public String price(String city, String sector){
        return priceService.price(city,sector).toString();
    }

    @ShellMethod("sector")
    public String sector(String city){
        return priceService.sectors(city).toString();
    }

    @ShellMethod("bill-total")
    public String billTotal(String city, String sector, int usage){
        return priceService.billTotal(city,sector,usage);
    }

}
