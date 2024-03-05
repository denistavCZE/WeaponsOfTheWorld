package tjv.semestralka.weaponsoftheworld.domain.dto.manufacturer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateManufacturer {
    private String name;
    private String country;
    private Long size;
}
