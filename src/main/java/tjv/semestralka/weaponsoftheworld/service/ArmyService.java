package tjv.semestralka.weaponsoftheworld.service;

import jakarta.persistence.EntityNotFoundException;
import tjv.semestralka.weaponsoftheworld.domain.Army;
import tjv.semestralka.weaponsoftheworld.domain.Gun;
import tjv.semestralka.weaponsoftheworld.domain.dto.army.NewArmy;
import tjv.semestralka.weaponsoftheworld.domain.dto.army.SimpleArmy;
import tjv.semestralka.weaponsoftheworld.domain.dto.army.UpdateArmy;

import java.util.List;

public interface ArmyService extends JpaService<Army, Long> {
    Army addArmy(NewArmy toAdd) throws IllegalArgumentException;

    List<SimpleArmy> getAllSimpleArmies();

    Army updateArmy(Long id, UpdateArmy armyUpdate) throws IllegalArgumentException, EntityNotFoundException;

    Army addGun(Long armyId, Long gunId) throws IllegalArgumentException, EntityNotFoundException;

    Army deleteGun(Long armyId, Long gunId) throws EntityNotFoundException;

    Gun readGunById(Long id) throws EntityNotFoundException;

    Long countGunsFromSameCountry(Long id) throws EntityNotFoundException;

}
