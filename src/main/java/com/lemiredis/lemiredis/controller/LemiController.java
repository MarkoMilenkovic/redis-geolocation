package com.lemiredis.lemiredis.controller;

import com.lemiredis.lemiredis.dto.LocationDto;
import com.lemiredis.lemiredis.dto.ShopDto;
import com.lemiredis.lemiredis.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LemiController {

    private final LocationService locationService;

    @PostMapping("/location/{shopId}")
    public String addLocation(@PathVariable String shopId, @RequestBody LocationDto locationDto) {
        locationService.add(shopId, locationDto);
        return "Success";
    }

    @DeleteMapping("/location/{shopId}")
    public void removeLocation(@PathVariable String shopId) {
        locationService.removeLocation(shopId);
    }

    @GetMapping("/location/nearby")
    public List<ShopDto> locations(Double lng, Double lat, Double km) {
        return locationService.getShopsNearLocation(lng, lat, km);
    }

    @GetMapping
    public String test() {
        return "kod mene radi";
    }
}
