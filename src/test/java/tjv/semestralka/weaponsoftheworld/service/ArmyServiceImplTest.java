package tjv.semestralka.weaponsoftheworld.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tjv.semestralka.weaponsoftheworld.domain.Army;
import tjv.semestralka.weaponsoftheworld.domain.Gun;
import tjv.semestralka.weaponsoftheworld.repository.ArmyRepository;
import tjv.semestralka.weaponsoftheworld.repository.GunRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@SpringBootTest
class ArmyServiceImplTest {
    @Autowired
    private ArmyServiceImpl armyService;
    @MockBean
    private ArmyRepository armyRepository;
    @MockBean
    private GunRepository gunRepository;
    Gun AK;
    Gun M4;
    Army Czechia;
    Army Slovakia;
    
    @BeforeEach
    void setUp() {
        AK = new Gun();
        M4 = new Gun();
        Czechia = new Army();
        Slovakia = new Army();

        AK.setId(1L);
        AK.setName("AK");
        AK.setGeneration(47L);
        AK.setRpm(470L);
        AK.setWeight(4.7f);

        M4.setId(2L);
        M4.setName("M4A4");
        M4.setGeneration(44L);
        M4.setRpm(440L);
        M4.setWeight(4.4f);

        Czechia.setId(1L);
        Czechia.setCountry("Czechia");
        Czechia.setSize(30000L);
        Czechia.setGuns(new HashSet<>());

        Slovakia.setId(2L);
        Slovakia.setCountry("Slovakia");
        Slovakia.setSize(10000L);
        Set<Gun> guns = new HashSet<>();
        guns.add(AK);
        guns.add(M4);
        Slovakia.setGuns(guns);

        Mockito.when(
                armyRepository.findById(Czechia.getId())
        ).thenReturn(Optional.of(Czechia));
        Mockito.when(
                armyRepository.findById(Slovakia.getId())
        ).thenReturn(Optional.of(Slovakia));
        Mockito.when(
                armyRepository.existsById(Czechia.getId())
        ).thenReturn(true);
        Mockito.when(
                armyRepository.existsById(Slovakia.getId())
        ).thenReturn(true);


        Mockito.when(
                gunRepository.findById(AK.getId())
        ).thenReturn(Optional.of(AK));
        Mockito.when(
                gunRepository.findById(M4.getId())
        ).thenReturn(Optional.of(M4));
        Mockito.when(
                gunRepository.existsById(AK.getId())
        ).thenReturn(true);
        Mockito.when(
                gunRepository.existsById(M4.getId())
        ).thenReturn(true);

    }

    @Test
    void addGunInvalidGunId() {
        Mockito.when(
                gunRepository.findById(AK.getId())
        ).thenReturn(Optional.empty());

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> armyService.addGun(Czechia.getId(), AK.getId())
        );
        Mockito.verify(armyRepository, Mockito.never()).save(Mockito.any());

    }
    @Test
    void addGunInvalidArmyId() {
        Mockito.when(
                armyRepository.findById(Czechia.getId())
        ).thenReturn(Optional.empty());

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> armyService.addGun(Czechia.getId(), AK.getId())
        );
        Mockito.verify(armyRepository, Mockito.never()).save(Mockito.any());
    }
    @Test
    void addDuplicateGun() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> armyService.addGun(Slovakia.getId(), AK.getId())
        );
        Mockito.verify(armyRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void addGunValid() {
        armyService.addGun(Czechia.getId(), AK.getId());
        Mockito.verify(armyRepository, Mockito.times(1)).save(Mockito.any());
    }
}