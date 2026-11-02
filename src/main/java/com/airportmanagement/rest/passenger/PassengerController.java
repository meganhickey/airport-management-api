package com.airportmanagement.rest.passenger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/passengers")
@RestController
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public Iterable<Passenger> getAllPassengers() {
        return passengerService.getAllPassengers();
    }

    @GetMapping("/paged")
    public Page<Passenger> getPassengersPaged(Pageable pageable) {
        return passengerService.getAllPassengers(pageable);
    }

    @GetMapping("/{id}")
    public Passenger getPassenger(@PathVariable Long id) {
        return passengerService.getPassengerById(id);
    }

    @PostMapping
    public Passenger createPassenger(@RequestBody Passenger passenger) {
        return passengerService.savePassenger(passenger);
    }

    @PutMapping("/{id}")
    public Passenger updatePassenger(@PathVariable Long id, @RequestBody Passenger passenger) {
        return passengerService.updatePassenger(id, passenger);
    }

    @DeleteMapping("/{id}")
    public void deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
    }
}
