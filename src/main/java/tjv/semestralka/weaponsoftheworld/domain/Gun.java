package tjv.semestralka.weaponsoftheworld.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "gun")
public class Gun implements EntityWithId<Long>{
    @Id
    @GeneratedValue(generator = "gun_seq")
    @SequenceGenerator(name = "gun_seq", sequenceName = "gun_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "generation")
    private Long generation;
    @Column(name = "rpm")
    private Long rpm;
    @Column(name = "weight")
    private float weight;
    @ManyToOne()
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;
    @ManyToMany(mappedBy = "guns")
    @JsonIgnore
    private Set<Army> armies = new HashSet<>();
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Gun that = (Gun) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
