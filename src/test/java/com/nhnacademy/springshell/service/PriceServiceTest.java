package com.nhnacademy.springshell.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.springshell.domain.Price;
import com.nhnacademy.springshell.exception.CityNotFoundException;
import com.nhnacademy.springshell.exception.SectorNotFoundException;
import com.nhnacademy.springshell.formatter.OutputFormatter;
import com.nhnacademy.springshell.parser.DataParser;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {
    @Mock
    private DataParser dataParser;

    @Mock
    private OutputFormatter outputFormatter;

    @Spy
    @InjectMocks
    private PriceService priceService;

    @Test
    public void getCities_Success(){
        List<String> expectedCities = List.of("광주시", "양주시");
        when(dataParser.cities()).thenReturn(expectedCities);

        List<String> actualCities = priceService.cities();

        assertThat(actualCities).isEqualTo(expectedCities);
    }

    @Test
    public void getSectors_Success(){
        List<String> expectedSectors = List.of("일반용", "가정용");
        when(dataParser.sectors(anyString())).thenReturn(expectedSectors);

        List<String> actualSectors = priceService.sectors("단양군");

        assertThat(actualSectors).isEqualTo(expectedSectors);
    }

    @Test
    public void getPrice_Success(){
        Price expectedPrice = new Price(1, "단양군","가정용", 2400);
        when(dataParser.price(anyString(), anyString())).thenReturn(expectedPrice);

        Price actualPrice = priceService.price("아무거나", "어떤 sector든");

        assertThat(actualPrice).isEqualTo(expectedPrice);
    }

    @Test
    public void getBillTotal_Success(){
        String city = "파주시";
        String sector = "가정용";
        int usage = 10;
        String expectedOutput = "요금 계산 결과입니다.";
        Price mockPrice = mock(Price.class);

        doReturn(mockPrice).when(priceService).price(city, sector);

        when(outputFormatter.format(mockPrice, usage)).thenReturn(expectedOutput);
        String result = priceService.billTotal(city, sector, usage);
        assertThat(result).isEqualTo(expectedOutput);
        verify(priceService).price(city, sector);
        verify(outputFormatter).format(mockPrice, usage);
    }

    @Test
    void getPrice_Exception_SectorNotFound() {
        assertThatThrownBy(() -> priceService.price("파주시", ""))
                .isInstanceOf(SectorNotFoundException.class);
    }

    @Test
    void getSectors_Exception_CityNotFound() {
        assertThatThrownBy(() -> priceService.sectors(null))
                .isInstanceOf(CityNotFoundException.class);

        assertThatThrownBy(() -> priceService.sectors("   "))
                .isInstanceOf(CityNotFoundException.class);
    }
}