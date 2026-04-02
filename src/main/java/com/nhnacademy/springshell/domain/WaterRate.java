package com.nhnacademy.springshell.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WaterRate {

    @CsvBindByName(column = "순번")
    @JsonProperty("순번")
    @Getter
    private long id;

    @CsvBindByName(column = "지자체명")
    @JsonProperty("지자체명")
    private String city;

    @CsvBindByName(column = "업종")
    @JsonProperty("업종")
    private String sector;

    @CsvBindByName(column = "단계")
    @JsonProperty("단계")
    private int step;

    @CsvBindByName(column = "구간시작(세제곱미터)")
    @JsonProperty("구간시작(세제곱미터)")
    private int startRage;

    @CsvBindByName(column = "구간끝(세제곱미터)")
    @JsonProperty("구간끝(세제곱미터)")
    private int endRage;

    @CsvBindByName(column = "구간금액(원)")
    @JsonProperty("구간금액(원)")
    @Getter
    private long unitPrice;

    @CsvBindByName(column = "단계별 기본요금(원)")
    @JsonProperty("단계별 기본요금(원)")
    private Long baseFee;

    public String getCity() {
        return city.trim();
    }

    public String getSector() {
        return sector.trim();
    }

}
