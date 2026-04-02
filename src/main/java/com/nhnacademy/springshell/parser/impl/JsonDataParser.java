package com.nhnacademy.springshell.parser.impl;

import com.nhnacademy.springshell.domain.Account;
import com.nhnacademy.springshell.domain.WaterRate;
import com.nhnacademy.springshell.parser.AbstractDataParser;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Component
@ConditionalOnProperty(value = "file.parser", havingValue = "json")
@Slf4j
@RequiredArgsConstructor
public class JsonDataParser extends AbstractDataParser {

    private final ObjectMapper objectMapper;

    @Value("${data.path.json.tariff}")
    private String tariffPath;

    @Value("${data.path.json.account}")
    private String accountPath;

    @PostConstruct
    public void loadData(){
        /// Tariff.json파싱
        try{
            InputStream inputStream = new ClassPathResource(tariffPath).getInputStream();

            List<WaterRate> tariff = objectMapper.readValue(inputStream, new TypeReference<List<WaterRate>>() {
            });

            waterRateMap = tariff.stream()
                    .collect(Collectors.groupingBy(WaterRate::getCity,
                            Collectors.groupingBy(WaterRate::getSector)));

        }catch (IOException e){
            log.error("Tariff.json 파싱 중 오류 발생: ", e);
        }

        /// account.json 파싱
        try{
            InputStream inputStream = new ClassPathResource(accountPath).getInputStream();

            List<Account> accounts = objectMapper.readValue(inputStream, new TypeReference<List<Account>>() {
            });

            accountsList = new ArrayList<>(accounts);
        }catch (IOException e){
            log.error("account.json 파싱 중 오류 발생: ", e);
        }
    }
}
