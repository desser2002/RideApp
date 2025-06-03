package domen.rideapp.api;

import domen.rideapp.api.request.InitRideRequest;
import domen.rideapp.api.response.RideResponse;
import domen.rideapp.domain.service.RideService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideController {
    private final RideService rideService;
    private final ConversionService conversionService;

    public RideController(RideService rideService, ConversionService conversionService) {
        this.rideService = rideService;
        this.conversionService = conversionService;
    }

    @PostMapping("/init")
    public ResponseEntity<Void> initRide(@RequestBody InitRideRequest request) {
        rideService.rideInitiation(request.customer(), request.from(), request.to());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<RideResponse>> getRides() {
        List<RideResponse> rides = rideService.getAllRides().stream()
                .map(ride -> conversionService.convert(ride, RideResponse.class))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(rides);
    }

    @PostMapping("/assign")
    public ResponseEntity<Integer> assignDrivers() {
        int assignRidesAmount = rideService.assignDriversToRides();
        return ResponseEntity.status(HttpStatus.OK).body(assignRidesAmount);
    }
}
