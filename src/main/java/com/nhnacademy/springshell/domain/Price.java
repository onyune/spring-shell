package com.nhnacademy.springshell.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Price {
    private long id;
    private String city;
    private String sector;
    private long unitPrice;

    @Override
    public String toString() {
        return "Price(id="+id+", city=" + city + ", sector=" + sector+ ", unitPrice="+unitPrice+")";
    }
}
