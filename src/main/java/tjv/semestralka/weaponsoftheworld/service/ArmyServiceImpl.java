package tjv.semestralka.weaponsoftheworld.service;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tjv.semestralka.weaponsoftheworld.domain.Army;
import tjv.semestralka.weaponsoftheworld.domain.Gun;
import tjv.semestralka.weaponsoftheworld.domain.dto.army.NewArmy;
import tjv.semestralka.weaponsoftheworld.domain.dto.army.SimpleArmy;
import tjv.semestralka.weaponsoftheworld.domain.dto.army.UpdateArmy;
import tjv.semestralka.weaponsoftheworld.repository.ArmyRepository;
import tjv.semestralka.weaponsoftheworld.repository.GunRepository;
import tjv.semestralka.weaponsoftheworld.repository.ManufacturerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArmyServiceImpl extends JpaServiceImpl<Army, Long> implements ArmyService{
    private final ArmyRepository armyRepository;
    private final GunRepository gunRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ModelMapper mapper;

    public ArmyServiceImpl(ArmyRepository armyRepository, GunRepository gunRepository, ManufacturerRepository manufacturerRepository, ModelMapper mapper){
        this.armyRepository = armyRepository;
        this.gunRepository = gunRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.mapper = mapper;
    }
    @Override
    protected JpaRepository<Army, Long> getRepository() {
        return armyRepository;
    }

    @Override
    public Gun readGunById(Long id){
        return gunRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Gun with id " + id + " not found"));
    }



    @Override
    public Army addArmy(NewArmy toAdd) {
        try{
            return armyRepository.save(mapper.map(toAdd,Army.class));
        }
        catch(DataIntegrityViolationException e){
            throw new IllegalArgumentException(toAdd.getCountry() + " already has an Army");
        }

    }

    @Override
    public List<SimpleArmy> getAllSimpleArmies() {
        return readAll().stream()
                .map(army -> mapper.map(army, SimpleArmy.class))
                .collect(Collectors.toList());
    }

    @Override
    public Army updateArmy(Long id, UpdateArmy armyUpdate) {
        Army army = readById(id);
        army.setCountry(armyUpdate.getCountry());
        army.setSize(armyUpdate.getSize());
        try {
            return update(army);
        }
        catch(DataIntegrityViolationException e){
            throw new IllegalArgumentException(armyUpdate.getCountry() + " already has an Army");
        }
    }

    @Override
    public Army addGun(Long armyId, Long gunId) {
        Gun gun = readGunById(gunId);
        Army army = readById(armyId);
        if(!army.getGuns().add(gun))
            throw new IllegalArgumentException("Army with id " + armyId + " already has gun with id " + gunId);
        return update(army);

    }

    @Override
    public Army deleteGun(Long armyId, Long gunId) {
        Army army = readById(armyId);
        Gun gun = readGunById(gunId);
        if(!army.getGuns().remove(gun))
            throw new EntityNotFoundException("Army with id " + armyId + " does not have gun with id " + gunId);
        return update(army);
    }

    @Override
    public Long countGunsFromSameCountry(Long id) throws EntityNotFoundException {
        readById(id);
        return armyRepository.countGunsFromSameCountry(id);
    }




}
