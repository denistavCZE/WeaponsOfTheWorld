package tjv.semestralka.weaponsoftheworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tjv.semestralka.weaponsoftheworld.domain.Gun;


public interface GunRepository extends JpaRepository<Gun, Long> {

}
