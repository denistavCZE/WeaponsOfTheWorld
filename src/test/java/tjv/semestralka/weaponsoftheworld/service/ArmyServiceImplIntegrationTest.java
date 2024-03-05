package tjv.semestralka.weaponsoftheworld.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tjv.semestralka.weaponsoftheworld.domain.Army;
import tjv.semestralka.weaponsoftheworld.domain.dto.army.UpdateArmy;
import tjv.semestralka.weaponsoftheworld.repository.ArmyRepository;
import tjv.semestralka.weaponsoftheworld.repository.GunRepository;

import java.util.HashSet;
import java.util.Optional;

@SpringBootTest
class ArmyServiceImplIntegrationTest {
    @Autowired
    private ArmyServiceImpl armyService;
    @Autowired
    private ArmyRepository armyRepository;
    @Autowired
    private GunRepository gunRepository;
    @Autowired
    ModelMapper mapper;

    Army testArmy;
    Army testArmy2;



    @BeforeEach
    void setUp() {

        testArmy = new Army();
        testArmy.setCountry("CzechiaTestovaci");
        testArmy.setSize(30000L);
        testArmy.setGuns(new HashSet<>());

        testArmy2 = new Army();
        testArmy2.setCountry("SlovakiaTestovaci");
        testArmy2.setSize(10000L);
        testArmy2.setGuns(new HashSet<>());



        testArmy = armyRepository.save(testArmy);
        testArmy2 = armyRepository.save(testArmy2);
    }
    @AfterEach
    void cleanUp(){
        armyRepository.delete(testArmy);
        armyRepository.delete(testArmy2);
    }


    @Test
    void updateArmyValid() {

        UpdateArmy updatedArmy = new UpdateArmy();
        updatedArmy.setCountry("CzechiaTest");
        updatedArmy.setSize(20000L);

        Army resultArmy;

        resultArmy = armyService.updateArmy(testArmy.getId(), updatedArmy);

        Assertions.assertEquals(resultArmy.getCountry(), updatedArmy.getCountry());
        Assertions.assertEquals(resultArmy.getSize(), updatedArmy.getSize());
        Assertions.assertEquals(resultArmy.getId(), testArmy.getId());

        testArmy = resultArmy;
    }

    @Test
    void updateArmyUniqueNameConflict() {

        UpdateArmy updatedArmy = new UpdateArmy();
        updatedArmy.setCountry("SlovakiaTestovaci");
        updatedArmy.setSize(20000L);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> armyService.updateArmy(testArmy.getId(), updatedArmy)
        );
        Optional<Army> resultArmy = armyRepository.findById(testArmy.getId());
        if(resultArmy.isPresent()) {
            Assertions.assertEquals(resultArmy.get().getCountry(), testArmy.getCountry());
            Assertions.assertEquals(resultArmy.get().getSize(), testArmy.getSize());
            Assertions.assertEquals(resultArmy.get().getId(), testArmy.getId());
        }
    }

    @Test
    void updateArmyInvalidId() {

        UpdateArmy updatedArmy = new UpdateArmy();
        updatedArmy.setCountry("Testovaci");
        updatedArmy.setSize(10000L);
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> armyService.updateArmy(0L, updatedArmy)
        );
        if(armyRepository.findById(0L).isPresent())
            armyRepository.delete(armyRepository.findById(0L).get());
    }

}