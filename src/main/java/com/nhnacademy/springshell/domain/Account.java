package com.nhnacademy.springshell.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Getter
    @CsvBindByName(column = "아이디")
    @JsonProperty("아이디")
    private long id;

    @CsvBindByName(column = "비밀번호")
    @JsonProperty("비밀번호")
    private String password;

    @CsvBindByName(column = "이름")
    @JsonProperty("이름")
    private String name;


    public String getPassword() {
        return password.trim();
    }

    public String getName() {
        return name.trim();
    }

    @Override
    public String toString() {
        return "Account(id="+id+", password="+password+", name="+name+")";
    }

}
