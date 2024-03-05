package tjv.semestralka.weaponsoftheworld.service;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tjv.semestralka.weaponsoftheworld.domain.Army;
import tjv.semestralka.weaponsoftheworld.domain.Manufacturer;
import tjv.semestralka.weaponsoftheworld.domain.Gun;
import tjv.semestralka.weaponsoftheworld.domain.dto.manufacturer.NewManufacturer;
import tjv.semestralka.weaponsoftheworld.domain.dto.manufacturer.SimpleManufacturer;
import tjv.semestralka.weaponsoftheworld.domain.dto.manufacturer.UpdateManufacturer;
import tjv.semestralka.weaponsoftheworld.repository.GunRepository;
import tjv.semestralka.weaponsoftheworld.repository.ManufacturerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManufacturerServiceImpl extends JpaServiceImpl<Manufacturer, Long> implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;
    private final GunRepository gunRepository;
    private final ModelMapper mapper;

    ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository, GunRepository gunRepository, ModelMapper mapper){
        this.manufacturerRepository = manufacturerRepository;
        this.gunRepository = gunRepository;
        this.mapper = mapper;
    }
    @Override
    protected JpaRepository<Manufacturer, Long> getRepository() {
        return manufacturerRepository;
    }

    @Override
    public Manufacturer addManufacturer(NewManufacturer toAdd) {
        try{
            return manufacturerRepository.save(mapper.map(toAdd,Manufacturer.class));
        }
        catch(DataIntegrityViolationException e){
            throw new IllegalArgumentException("Manufacturer " + toAdd.getName() + " already exists");
        }

    }

    @Override
    public List<SimpleManufacturer> getAllSimpleManufacturers() {
        return readAll().stream()
                .map(manufacturer -> mapper.map(manufacturer, SimpleManufacturer.class))
                .collect(Collectors.toList());
    }

    @Override
    public Manufacturer updateManufacturer(Long id, UpdateManufacturer manufacturerUpdate) {
        Manufacturer manufacturer = readById(id);
        manufacturer.setName(manufacturerUpdate.getName());
        manufacturer.setCountry(manufacturerUpdate.getCountry());
        manufacturer.setSize(manufacturerUpdate.getSize());
        try {
            return update(manufacturer);
        }
        catch(DataIntegrityViolationException e){
            throw new IllegalArgumentException(manufacturerUpdate.getCountry() + " already has an Manufacturer");
        }
    }
    public void deleteManufacturer(Long id) {
        Manufacturer manufacturer = readById(id);

        for (Gun gun : manufacturer.getGuns()) {
            for(Army army : gun.getArmies()){
                army.getGuns().remove(gun);
            }
            gun.getArmies().clear();
            gunRepository.delete(gun);
        }

        deleteById(id);
    }


}
