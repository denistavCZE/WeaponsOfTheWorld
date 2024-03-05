package tjv.semestralka.weaponsoftheworld.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tjv.semestralka.weaponsoftheworld.domain.Army;
import tjv.semestralka.weaponsoftheworld.domain.dto.army.NewArmy;
import tjv.semestralka.weaponsoftheworld.repository.ArmyRepository;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashSet;


@SpringBootTest
@AutoConfigureMockMvc
class ArmyControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ArmyRepository armyRepository;
    Army testArmy;
    Army testArmy2;



    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        testArmy = new Army();
        testArmy.setCountry("SlovakiaTestovaci");
        testArmy.setSize(30000L);
        testArmy.setGuns(new HashSet<>());

        testArmy = armyRepository.save(testArmy);
   }

    @AfterEach
    void cleanUp(){
        armyRepository.delete(testArmy);
        if(testArmy2 != null)
            armyRepository.delete(testArmy2);
    }

    @Test
    void addArmyValid() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        NewArmy newArmy = new NewArmy();
        newArmy.setCountry("CzechiaTestovaci");
        newArmy.setSize(30000L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/armies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newArmy)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.country").value(newArmy.getCountry()))
                .andExpect(jsonPath("$.size").value(newArmy.getSize()))
                .andExpect(jsonPath("$.guns").isArray())
                .andExpect(jsonPath("$.guns").isEmpty())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        testArmy2 = objectMapper.readValue(content, Army.class);
    }

    @Test
    void addArmyUniqueNameConflict() throws Exception {
        NewArmy newArmy = new NewArmy();
        newArmy.setCountry("SlovakiaTestovaci");
        newArmy.setSize(20000L);

        mockMvc.perform(MockMvcRequestBuilders.post("/armies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newArmy)))
                .andExpect(status().isConflict());
        int sameCount = 0;
        for(Army army : armyRepository.findAll()){
            if(army.getCountry().equals(newArmy.getCountry()))
                sameCount++;
        }
        Assertions.assertEquals(sameCount, 1);
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}