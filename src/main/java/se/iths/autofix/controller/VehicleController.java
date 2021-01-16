package se.iths.autofix.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.autofix.entity.Maintenance;
import se.iths.autofix.entity.Vehicle;
import se.iths.autofix.exception.BadInputFormatException;
import se.iths.autofix.exception.MaintenanceNotFoundException;
import se.iths.autofix.exception.VehicleNotFoundException;
import se.iths.autofix.service.VehicleService;

import java.util.Optional;

@RestController
@RequestMapping(path={"/api/vehicle"})
public class  VehicleController {
    Logger logger = LoggerFactory.getLogger(VehicleController.class);

    private VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/create")
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
        logger.info("createVehicle() was called with number plate: " + vehicle.getNumberPlate());
        try {
            return vehicleService.createVehicle(vehicle);
        }catch (BadInputFormatException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The input is incorrect", e);
        }
    }

    @PutMapping("/update/{id}")
    public Vehicle updateVehicle(@RequestBody Vehicle newVehicle, @PathVariable Long id) {
        return vehicleService.updateVehicle(newVehicle, id);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ADMIN')")
    @GetMapping("/findall")
    public Iterable<Vehicle> findAllVehicles() {
        try {
            return vehicleService.findAllVehicles();
        }catch (VehicleNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle Not Found", e);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findVehicleById(@PathVariable Long id) {
        if(id<=0){
            throw new VehicleNotFoundException("The vehicle id was not found.");
        }
        return ResponseEntity.ok(vehicleService.findVehicleById(id));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
    }
}
