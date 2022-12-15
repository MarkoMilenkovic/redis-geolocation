package com.lemiredis.lemiredis.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopDto {

    private String id;
    private Double lat;
    private Double lng;
    private Double distance;
}
