package com.nhnacademy.springshell.formatter;

import com.nhnacademy.springshell.domain.Price;

public interface OutputFormatter {
    String format(Price price, int usage);
}
