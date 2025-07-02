package domen.rideapp.infrastructure.repository;

import domen.rideapp.domain.model.*;
import domen.rideapp.domain.repository.RideCacheRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers (disabledWithoutDocker = true)
class RedisCacheRideRepositoryIntegrationTest {
    @Container
    static GenericContainer<?> redisContainer = new GenericContainer<>("redis:7-alpine")
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void overrideRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));
    }

    @Autowired
    private RideCacheRepository rideCacheRepository;

    @BeforeEach
    void cleanRedis() {
        rideCacheRepository.clear();
    }

    @Test
    void shouldSaveAndRetrieveRide() {
        //given
        GeoPoint from = new GeoPoint(52.2297, 21.0122);
        GeoPoint to = new GeoPoint(51.1079, 17.0385);
        Localization localization = new Localization(from, to);
        Ride ride = new Ride("Customer1", localization, RideStatus.PENDING, new Price("12.01 PLN"));

        //when
        rideCacheRepository.save(ride);
        List<Ride> allRides = rideCacheRepository.getPendingRides();

        //then
        assertEquals(1, allRides.size());
        assertEquals(ride.getId(), allRides.getFirst().getId());
    }

    @Test
    void shouldDeleteRideById() {
        //given
        GeoPoint from = new GeoPoint(52.2297, 21.0122);
        GeoPoint to = new GeoPoint(51.1079, 17.0385);
        Localization localization = new Localization(from, to);
        Ride ride = new Ride("Customer1", localization, RideStatus.PENDING, new Price("12.01 PLN"));
        rideCacheRepository.save(ride);

        //when
        rideCacheRepository.deleteBatch(List.of(ride.getId()));
        List<Ride> allRides = rideCacheRepository.getPendingRides();

        //then
        assertTrue(allRides.isEmpty());
    }

    @Test
    void shouldClearAllRides() {
        //given
        GeoPoint from = new GeoPoint(52.2297, 21.0122);
        GeoPoint to = new GeoPoint(51.1079, 17.0385);
        Localization localization = new Localization(from, to);
        Ride ride1 = new Ride("Customer1", localization, RideStatus.PENDING, new Price("12.01 PLN"));
        Ride ride2 = new Ride("Customer2", localization, RideStatus.PENDING, new Price("12.01 PLN"));
        Ride ride3 = new Ride("Customer3", localization, RideStatus.PENDING, new Price("12.01 PLN"));
        rideCacheRepository.save(ride1);
        rideCacheRepository.save(ride2);
        rideCacheRepository.save(ride3);

        //when
        rideCacheRepository.clear();
        List<Ride> allRides = rideCacheRepository.getPendingRides();

        //then
        assertTrue(allRides.isEmpty());
    }
}