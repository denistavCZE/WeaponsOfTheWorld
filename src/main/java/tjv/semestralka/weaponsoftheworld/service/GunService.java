package tjv.semestralka.weaponsoftheworld.service;

import jakarta.persistence.EntityNotFoundException;
import tjv.semestralka.weaponsoftheworld.domain.Gun;
import tjv.semestralka.weaponsoftheworld.domain.Manufacturer;
import tjv.semestralka.weaponsoftheworld.domain.dto.gun.NewGun;
import tjv.semestralka.weaponsoftheworld.domain.dto.gun.SimpleGun;
import tjv.semestralka.weaponsoftheworld.domain.dto.gun.UpdateGun;

import java.util.List;

public interface GunService extends JpaService<Gun, Long>{
    SimpleGun addGun(NewGun toCreate) throws EntityNotFoundException;

    List<SimpleGun> getAllSimpleGuns();

    Gun updateGun(Long id, UpdateGun gunUpdate) throws IllegalArgumentException, EntityNotFoundException;
    Gun updateGunManufacturer(Long gunId, Long manufacturerId) throws IllegalArgumentException, EntityNotFoundException;
    void deleteGun(Long id) throws EntityNotFoundException;
    Manufacturer readManufacturerById(Long id) throws EntityNotFoundException;
}
