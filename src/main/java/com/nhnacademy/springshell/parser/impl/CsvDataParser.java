package com.nhnacademy.springshell.parser.impl;

import com.nhnacademy.springshell.domain.Account;
import com.nhnacademy.springshell.domain.WaterRate;
import com.nhnacademy.springshell.parser.AbstractDataParser;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.annotation.PostConstruct;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(matchIfMissing=true, value = "file.parser", havingValue = "csv")
public class CsvDataParser extends AbstractDataParser {
    @Value("${data.path.csv.tariff}")
    private String tariffPath;

    @Value("${data.path.csv.account}")
    private String accountPath;

    @PostConstruct
    public void loadData(){
        /// Tariff.csv 파싱
        try(InputStreamReader reader = new InputStreamReader(
                new ClassPathResource(tariffPath).getInputStream(),
                StandardCharsets.UTF_8
        )){
            List<WaterRate> rates = new CsvToBeanBuilder<WaterRate>(reader)
                    .withType(WaterRate.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build().parse();

            waterRateMap = rates.stream()
                    .collect(Collectors.groupingBy(WaterRate::getCity,
                            Collectors.groupingBy(WaterRate::getSector)));

        } catch (Exception e) {
            log.error("Tariff.csv 파싱 중 에러 발생");
        }

        /// Account.csv 파싱
        try(InputStreamReader reader = new InputStreamReader(
                new ClassPathResource(accountPath).getInputStream(),
                StandardCharsets.UTF_8
        )){
            List<Account> accounts = new CsvToBeanBuilder<Account>(reader)
                    .withType(Account.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();

            accountsList = new ArrayList<>(accounts);
        } catch (Exception e) {
            log.error("account 에러: ",e);
        }
    }

}
