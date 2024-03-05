package tjv.semestralka.weaponsoftheworld.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tjv.semestralka.weaponsoftheworld.domain.Gun;
import tjv.semestralka.weaponsoftheworld.domain.dto.gun.NewGun;
import tjv.semestralka.weaponsoftheworld.domain.dto.gun.SimpleGun;
import tjv.semestralka.weaponsoftheworld.domain.dto.gun.UpdateGun;
import tjv.semestralka.weaponsoftheworld.service.GunService;

import java.util.List;

@RestController
@RequestMapping("guns")
public class GunController {
    private final GunService gunService;

    public GunController(GunService gunService){
        this.gunService = gunService;
    }

    @PostMapping()
    @Operation(description = "Register new gun")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gun was created successfully")
    })
    public SimpleGun addGun(@RequestBody NewGun toAdd){
        try{
            return gunService.addGun(toAdd);
        }
        catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping()
    @Operation(description = "List all guns")
    public List<SimpleGun> getAllGuns(){
        return gunService.getAllSimpleGuns();
    }

    @GetMapping("/{id}")
    @Operation(description = "Show gun detail")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gun was successfully found"),
            @ApiResponse(responseCode = "404", description = "Gun not found")
    })
    public Gun getGun(@PathVariable Long id){
        try{
            return gunService.readById(id);
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PutMapping("{id}")
    @Operation(description = "Update existing Gun")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gun was successfully updated"),
            @ApiResponse(responseCode = "404", description = "Gun or Manufacturer not found")
    })
    public Gun updateGun(@PathVariable Long id, @RequestBody UpdateGun gunUpdate){
        try {
            return gunService.updateGun(id, gunUpdate);
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PutMapping("{gunId}/{manufacturerId}")
    @Operation(description = "Update existing Gun's Manufacturer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gun was successfully updated"),
            @ApiResponse(responseCode = "404", description = "Gun or Manufacturer not found")
    })
    public Gun updateGunManufacturer(@PathVariable Long gunId, @PathVariable Long manufacturerId){
        try {
            return gunService.updateGunManufacturer(gunId, manufacturerId);
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(description = "Delete existing gun")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gun was successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Gun not found")
    })
    public void deleteGun(@PathVariable Long id){
        try{
            gunService.deleteGun(id);
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
