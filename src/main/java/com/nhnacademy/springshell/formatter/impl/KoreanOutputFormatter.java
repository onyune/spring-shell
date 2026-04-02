package com.nhnacademy.springshell.formatter.impl;

import com.nhnacademy.springshell.domain.Price;
import com.nhnacademy.springshell.formatter.OutputFormatter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("ko")
@Component
public class KoreanOutputFormatter implements OutputFormatter {
    @Override
    public String format(Price price, int usage) {
        long totalPrice = price.getUnitPrice() * usage;

        return String.format("지자체명: %s, 업종: %s, 구간금액(원): %d, 총금액(원): %d",
                price.getCity(), price.getSector(), price.getUnitPrice(), totalPrice);
    }
}
