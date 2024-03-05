package tjv.semestralka.weaponsoftheworld.domain.dto.gun;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateGun {
    private String name;
    private Long generation;
    private Long rpm;
    private float weight;
    private Long manufacturer;
}
