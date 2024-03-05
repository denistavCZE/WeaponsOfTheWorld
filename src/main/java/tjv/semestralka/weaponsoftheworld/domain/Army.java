package tjv.semestralka.weaponsoftheworld.domain;

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
@Table(name = "army")
public class Army implements EntityWithId<Long>{
    @Id
    @GeneratedValue(generator = "army_seq")
    @SequenceGenerator(name = "army_seq", sequenceName = "army_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "country", unique = true)
    private String country;
    @Column(name = "size")
    private Long size;
    @ManyToMany()
    @JoinTable(
            name = "army_gun",
            joinColumns = @JoinColumn(name = "army_id"),
            inverseJoinColumns = @JoinColumn(name = "gun_id")
    )
    private Set<Gun> guns = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Army army = (Army) o;
        return id != null && Objects.equals(id, army.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
