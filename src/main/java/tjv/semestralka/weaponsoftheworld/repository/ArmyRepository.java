package tjv.semestralka.weaponsoftheworld.repository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tjv.semestralka.weaponsoftheworld.domain.Army;

public interface ArmyRepository extends JpaRepository<Army, Long> {
    @Query(value = "SELECT COUNT(g) FROM Army a " +
                        "JOIN a.guns g " +
                        "JOIN g.manufacturer m " +
                        "WHERE a.id = :armyId " +
                        "AND m.country = a.country")
    Long countGunsFromSameCountry(Long armyId) throws EntityNotFoundException;


}
