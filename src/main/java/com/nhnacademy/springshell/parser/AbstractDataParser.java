package com.nhnacademy.springshell.parser;

import com.nhnacademy.springshell.domain.Account;
import com.nhnacademy.springshell.domain.Price;
import com.nhnacademy.springshell.domain.WaterRate;
import java.util.List;
import java.util.Map;

public abstract class AbstractDataParser implements DataParser{
    protected  Map<String, Map<String, List<WaterRate>>> waterRateMap; //city -> sector -> waterRate
    protected List<Account> accountsList;

    @Override
    public Price price(String city, String sector) {
        Map<String, List<WaterRate>> sectorMap = waterRateMap.get(city);
        List<WaterRate> waterRateList = sectorMap.getOrDefault(sector, List.of());
        if(waterRateList.isEmpty()){
            return null;
        }
        WaterRate firstStepRate = waterRateList.getFirst();

        return new Price(firstStepRate.getId(),
                firstStepRate.getCity(),
                firstStepRate.getSector(),
                firstStepRate.getUnitPrice());
    }

    @Override
    public List<Account> accounts() {
        return accountsList;
    }

    @Override
    public List<String> sectors(String city) {
        Map<String, List<WaterRate>> values = waterRateMap.getOrDefault(city, Map.of());
        return values.keySet().stream().toList();
    }

    @Override
    public List<String> cities() {
        return waterRateMap.keySet().stream().toList();
    }
}
