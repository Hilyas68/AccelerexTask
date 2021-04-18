package com.globalaccelerex.openinghours.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenHoursDto {
    private String type;
    private long value;
}
