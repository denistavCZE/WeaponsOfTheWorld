package tjv.semestralka.weaponsoftheworld.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tjv.semestralka.weaponsoftheworld.domain.Army;
import tjv.semestralka.weaponsoftheworld.domain.dto.army.NewArmy;
import tjv.semestralka.weaponsoftheworld.domain.dto.army.SimpleArmy;
import tjv.semestralka.weaponsoftheworld.domain.dto.army.UpdateArmy;
import tjv.semestralka.weaponsoftheworld.service.ArmyService;

import java.util.List;

@RestController
@RequestMapping("armies")
public class ArmyController {
    private final ArmyService armyService;

    public ArmyController(ArmyService armyService){
        this.armyService = armyService;
    }

    @PostMapping()
    @Operation(description = "Register new army")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "409", description = "Given country already has an army")
    })
    public Army addArmy(@RequestBody NewArmy toAdd){
        try{
            return armyService.addArmy(toAdd);
        }
        catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping()
    @Operation(description = "List all armies")
    public List<SimpleArmy> getAllArmies(){
        return armyService.getAllSimpleArmies();
    }

    @GetMapping("/{id}")
    @Operation(description = "Show army detail")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Army was successfully found"),
            @ApiResponse(responseCode = "404", description = "Army not found")
    })
    public Army getArmy(@PathVariable Long id){
        try{
            return armyService.readById(id);
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PutMapping("{id}")
    @Operation(description = "Update existing army")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Army was successfully updated"),
            @ApiResponse(responseCode = "404", description = "Army not found"),
            @ApiResponse(responseCode = "409", description = "Given country already has an army")
    })
    public Army updateArmy(@PathVariable Long id, @RequestBody UpdateArmy armyUpdate){
        try {
            return armyService.updateArmy(id, armyUpdate);
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    @Operation(description = "Delete existing army")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Army was successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Army not found")
    })
    public void deleteArmy(@PathVariable Long id){
        try{
            armyService.deleteById(id);
        }
        catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/{armyId}/guns/{gunId}")
    @Operation(description = "Add new gun to army")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gun was successfully added to army"),
            @ApiResponse(responseCode = "404", description = "Army or Gun not found"),
            @ApiResponse(responseCode = "409", description = "Army already has that weapon"),
            @ApiResponse(responseCode = "422", description = "Different country Gun Manufacturer limit exceeded")
    })
    public Army addGun(@PathVariable Long armyId, @PathVariable Long gunId){
        try {
            return armyService.addGun(armyId, gunId);
        }
        catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
    @DeleteMapping("{armyId}/guns/{gunId}")
    @Operation(description = "Delete gun from army")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gun was successfully deleted from army"),
            @ApiResponse(responseCode = "404", description = "Army or Gun not found")
    })
    public Army deleteGun(@PathVariable Long armyId, @PathVariable Long gunId){
        try{
            return armyService.deleteGun(armyId, gunId);
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @GetMapping("/{id}/guns-from-same-country")
    @Operation(description = "Count Guns from the same Country for a given Army")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Weapons counted successfully"),
            @ApiResponse(responseCode = "404", description = "Army not found")
    })
    public Long countWeaponsFromSameCountry(@PathVariable Long id) {
        try {
            return armyService.countGunsFromSameCountry(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
