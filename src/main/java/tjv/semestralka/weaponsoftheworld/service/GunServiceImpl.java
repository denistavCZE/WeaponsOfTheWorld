package tjv.semestralka.weaponsoftheworld.service;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tjv.semestralka.weaponsoftheworld.domain.Army;
import tjv.semestralka.weaponsoftheworld.domain.Gun;
import tjv.semestralka.weaponsoftheworld.domain.Manufacturer;
import tjv.semestralka.weaponsoftheworld.domain.dto.gun.NewGun;
import tjv.semestralka.weaponsoftheworld.domain.dto.gun.SimpleGun;
import tjv.semestralka.weaponsoftheworld.domain.dto.gun.UpdateGun;
import tjv.semestralka.weaponsoftheworld.repository.ArmyRepository;
import tjv.semestralka.weaponsoftheworld.repository.GunRepository;
import tjv.semestralka.weaponsoftheworld.repository.ManufacturerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GunServiceImpl extends JpaServiceImpl<Gun, Long> implements GunService{
    private final GunRepository gunRepository;
    private final ArmyRepository armyRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ModelMapper mapper;

    public GunServiceImpl(GunRepository gunRepository, ArmyRepository armyRepository, ManufacturerRepository manufacturerRepository, ModelMapper mapper ) {
        this.gunRepository = gunRepository;
        this.armyRepository = armyRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.mapper = mapper;
    }


    @Override
    protected JpaRepository<Gun, Long> getRepository() {
        return gunRepository;
    }

    @Override
    public Manufacturer readManufacturerById(Long id){
        return manufacturerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Manufacturer with id " + id + " not found"));
    }
    @Override
    public SimpleGun addGun(NewGun toAdd) {
        Manufacturer manufacturer = readManufacturerById(toAdd.getManufacturer());
        Gun gun = mapper.map(toAdd, Gun.class);
        gun.setManufacturer(manufacturer);
        return mapper.map(create(gun), SimpleGun.class);
    }

    @Override
    public List<SimpleGun> getAllSimpleGuns() {
        return readAll().stream()
                .map(gun -> mapper.map(gun, SimpleGun.class))
                .collect(Collectors.toList());
    }

    @Override
    public Gun updateGun(Long id, UpdateGun gunUpdate) {
        Gun gun = readById(id);
        gun.setName(gunUpdate.getName());
        gun.setGeneration(gunUpdate.getGeneration());
        gun.setRpm(gunUpdate.getRpm());
        gun.setWeight(gunUpdate.getWeight());
        gun.setManufacturer(readManufacturerById(gunUpdate.getManufacturer()));
        return update(gun);
    }

    @Override
    public Gun updateGunManufacturer(Long gunId, Long manufacturerId) throws IllegalArgumentException, EntityNotFoundException {
        Gun gun = readById(gunId);
        gun.setManufacturer(readManufacturerById(manufacturerId));
        return update(gun);
    }

    @Override
    public void deleteGun(Long id) {
        Gun gun = readById(id);
        for(Army army : gun.getArmies()){
            army.getGuns().remove(gun);
        }
        deleteById(id);
    }

}
