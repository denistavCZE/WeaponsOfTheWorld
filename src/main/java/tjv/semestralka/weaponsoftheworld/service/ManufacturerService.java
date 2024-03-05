package tjv.semestralka.weaponsoftheworld.service;

import jakarta.persistence.EntityNotFoundException;
import tjv.semestralka.weaponsoftheworld.domain.Gun;
import tjv.semestralka.weaponsoftheworld.domain.Manufacturer;
import tjv.semestralka.weaponsoftheworld.domain.dto.manufacturer.NewManufacturer;
import tjv.semestralka.weaponsoftheworld.domain.dto.manufacturer.SimpleManufacturer;
import tjv.semestralka.weaponsoftheworld.domain.dto.manufacturer.UpdateManufacturer;

import java.util.List;

public interface ManufacturerService extends JpaService<Manufacturer, Long>{
    Manufacturer addManufacturer(NewManufacturer toAdd)throws IllegalArgumentException;

    List<SimpleManufacturer> getAllSimpleManufacturers();

    Manufacturer updateManufacturer(Long id, UpdateManufacturer manufacturerUpdate) throws EntityNotFoundException, IllegalArgumentException;

    void deleteManufacturer(Long id) throws EntityNotFoundException;



}
