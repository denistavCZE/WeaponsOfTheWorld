package tjv.semestralka.weaponsoftheworld.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tjv.semestralka.weaponsoftheworld.domain.Manufacturer;
import tjv.semestralka.weaponsoftheworld.domain.dto.manufacturer.NewManufacturer;
import tjv.semestralka.weaponsoftheworld.domain.dto.manufacturer.SimpleManufacturer;
import tjv.semestralka.weaponsoftheworld.domain.dto.manufacturer.UpdateManufacturer;
import tjv.semestralka.weaponsoftheworld.service.ManufacturerService;

import java.util.List;

@RestController
@RequestMapping("/manufacturers")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService){
        this.manufacturerService = manufacturerService;
    }
    @PostMapping()
    @Operation(description = "Register new manufacturer")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "409", description = "Given country already has an manufacturer")
    })
    public Manufacturer addManufacturer(@RequestBody NewManufacturer toAdd){
        try{
            return manufacturerService.addManufacturer(toAdd);
        }
        catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping()
    @Operation(description = "List all manufacturers")
    public List<SimpleManufacturer> getAllManufacturers(){
        return manufacturerService.getAllSimpleManufacturers();
    }

    @GetMapping("/{id}")
    @Operation(description = "Show manufacturer detail")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Manufacturer was successfully found"),
            @ApiResponse(responseCode = "404", description = "Manufacturer not found")
    })
    public Manufacturer getManufacturer(@PathVariable Long id){
        try{
            return manufacturerService.readById(id);
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PutMapping("{id}")
    @Operation(description = "Update existing manufacturer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Manufacturer was successfully updated"),
            @ApiResponse(responseCode = "404", description = "Manufacturer not found"),
            @ApiResponse(responseCode = "409", description = "Given country already has an manufacturer")
    })
    public Manufacturer updateManufacturer(@PathVariable Long id, @RequestBody UpdateManufacturer manufacturerUpdate){
        try {
            return manufacturerService.updateManufacturer(id, manufacturerUpdate);
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    @Operation(description = "Delete existing manufacturer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Manufacturer was successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Manufacturer not found")
    })
    public void deleteManufacturer(@PathVariable Long id){
        try{
            manufacturerService.deleteManufacturer(id);
        }
        catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
