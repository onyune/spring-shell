package com.nhnacademy.springshell.service;

import com.nhnacademy.springshell.domain.Price;
import com.nhnacademy.springshell.exception.CityNotFoundException;
import com.nhnacademy.springshell.exception.SectorNotFoundException;
import com.nhnacademy.springshell.formatter.OutputFormatter;
import com.nhnacademy.springshell.parser.DataParser;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PriceService {
    private final DataParser dataParser;
    private final OutputFormatter outputFormatter;

    public List<String> sectors(String city){
        if(validationCity(city)){
            throw new CityNotFoundException();
        }
        return dataParser.sectors(city);
    }

    public String billTotal(String city, String sector, int usage){
        if(validationCity(city)){
            throw new CityNotFoundException();
        }
        if(validationSector(sector)){
            throw new SectorNotFoundException();
        }
        Price price = price(city,sector);
        return outputFormatter.format(price, usage);
    }

    public Price price(String city, String sector){
        if(validationCity(city)){
            throw new CityNotFoundException();
        }
        if(validationSector(sector)){
            throw new SectorNotFoundException();
        }

        return dataParser.price(city,sector);
    }

    public List<String> cities (){
        return dataParser.cities();
    }


    ///  내부 메서드
    private boolean validationCity(String city){
        return Objects.isNull(city) || city.isBlank();
    }

    private boolean validationSector(String sector){
        return Objects.isNull(sector) || sector.isBlank();
    }
}
