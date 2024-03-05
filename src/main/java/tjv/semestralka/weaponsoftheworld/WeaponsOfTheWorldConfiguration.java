package tjv.semestralka.weaponsoftheworld;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tjv.semestralka.weaponsoftheworld.domain.Army;
import tjv.semestralka.weaponsoftheworld.domain.Gun;
import tjv.semestralka.weaponsoftheworld.domain.dto.army.NewArmy;
import tjv.semestralka.weaponsoftheworld.domain.dto.gun.NewGun;
import tjv.semestralka.weaponsoftheworld.domain.dto.gun.SimpleGun;
import tjv.semestralka.weaponsoftheworld.domain.dto.gun.UpdateGun;

@Configuration
public class WeaponsOfTheWorldConfiguration {
    @Bean
    public ModelMapper modelMapperBean(){
        var modelMapper = new ModelMapper();
        modelMapper.createTypeMap(NewGun.class, Gun.class);
        modelMapper.createTypeMap(Gun.class, SimpleGun.class);
        modelMapper.createTypeMap(NewArmy.class, Army.class);
        modelMapper.createTypeMap(UpdateGun.class, Gun.class);
        return modelMapper;
    }
}
