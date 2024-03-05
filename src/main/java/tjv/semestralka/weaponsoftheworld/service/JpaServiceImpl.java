package tjv.semestralka.weaponsoftheworld.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import tjv.semestralka.weaponsoftheworld.domain.EntityWithId;

import java.util.List;


public abstract class JpaServiceImpl<T extends EntityWithId<ID>, ID> implements JpaService<T, ID>{
    @Override
    public T create(T e) {
        return getRepository().save(e);
    }

    @Override
    public T readById(ID id) {
        return getRepository().findById(id).orElseThrow(() -> new EntityNotFoundException(getEntityName() + " with id " + id + " not found"));
    }

    @Override
    public List<T> readAll() {
        return getRepository().findAll();
    }

    @Override
    public T update(T e) {
        if (!getRepository().existsById(e.getId()))
            throw new EntityNotFoundException(getEntityName() + " with id " + e.getId() + " was not found");
        return getRepository().save(e);
    }

    @Override
    public void deleteById(ID id) {
        T toDel = readById(id);
        getRepository().delete(toDel);
    }

    protected abstract JpaRepository<T, ID> getRepository();
    @Override
    public String getEntityName(){
        return getClass().getSimpleName().replaceAll("ServiceImpl","");
    }
}
