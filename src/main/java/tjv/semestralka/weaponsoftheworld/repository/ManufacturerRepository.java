package tjv.semestralka.weaponsoftheworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tjv.semestralka.weaponsoftheworld.domain.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
