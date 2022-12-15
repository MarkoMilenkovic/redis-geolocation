package com.lemiredis.lemiredis.service;

import com.lemiredis.lemiredis.dto.LocationDto;
import com.lemiredis.lemiredis.dto.ShopDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.domain.geo.GeoLocation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    public static final String SHOPS = "primo-shops";

    private final GeoOperations<String, String> geoOperations;

    public void add(String shopId, LocationDto locationDto) {
        Point point = new Point(locationDto.getLng(), locationDto.getLat());
        geoOperations.add(SHOPS, point, shopId);
    }

    public List<ShopDto> getShopsNearLocation(Double lng, Double lat, Double kmDistance) {
        Circle circle = new Circle(new Point(lng, lat), new Distance(kmDistance, Metrics.KILOMETERS));
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeDistance()
                .includeCoordinates();

        GeoResults<RedisGeoCommands.GeoLocation<String>> res = geoOperations.radius(SHOPS, circle, args);

        return Optional.ofNullable(res)
                .orElseThrow(() -> new RuntimeException("not found"))
                .getContent()
                .stream()
                .map(e -> getShop(e.getDistance(), e.getContent()))
                .collect(Collectors.toList());

    }

    private ShopDto getShop(Distance distance, GeoLocation<String> location) {
        return ShopDto.builder().id(location.getName()).lat(location.getPoint().getX())
                .lng(location.getPoint().getY())
                .distance(distance.getValue())
                .build();
    }

    public void removeLocation(String shopId) {
        geoOperations.remove(SHOPS, shopId);
    }
}
