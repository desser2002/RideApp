package domen.rideapp.api;

import domen.rideapp.api.request.AddDriverRequest;
import domen.rideapp.api.request.UpdateDriverRequest;
import domen.rideapp.api.request.UpdateDriverStatusRequest;
import domen.rideapp.api.response.DriverResponse;
import domen.rideapp.domain.service.DriverService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final DriverService driverService;
    private final ConversionService conversionService;

    public DriverController(DriverService driverService, ConversionService conversionService) {
        this.driverService = driverService;
        this.conversionService = conversionService;
    }

    @GetMapping
    public ResponseEntity<List<DriverResponse>> getAllDrivers() {
        List<DriverResponse> drivers = driverService.getAllDrivers().stream()
                .map(driver -> conversionService.convert(driver, DriverResponse.class))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(drivers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponse> getDriver(@PathVariable String id) {
        return driverService.getDriverById(id)
                .map(driver -> conversionService.convert(driver, DriverResponse.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addDriver(@RequestBody AddDriverRequest request) {
        driverService.add(request.firstName(), request.lastName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeDriver(@PathVariable String id) {
        driverService.remove(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDriver(@RequestBody UpdateDriverRequest request, @PathVariable String id) {
        driverService.update(id, request.firstName(), request.lastName(), request.driverStatus());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateDriverStatus(@PathVariable String id,
                                                   @RequestBody UpdateDriverStatusRequest request) {
        if (request.status() == null) {
            return ResponseEntity.badRequest().build();
        }
        driverService.updateStatus(id, request.status());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
