package com.lemiredis.lemiredis.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDto {

    private Double lat;
    private Double lng;

}
