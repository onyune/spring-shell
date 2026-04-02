package com.nhnacademy.springshell.formatter.impl;

import com.nhnacademy.springshell.domain.Price;
import com.nhnacademy.springshell.formatter.OutputFormatter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("en")
public class EnglishOutputFormatter implements OutputFormatter {
    @Override
    public String format(Price price, int usage) {
        long totalPrice = price.getUnitPrice() * usage;

        return String.format("city: %s, sector: %s, unit price(won): %d, bill total(won): %d",
                price.getCity(), price.getSector(), price.getUnitPrice(), totalPrice);
    }
}
