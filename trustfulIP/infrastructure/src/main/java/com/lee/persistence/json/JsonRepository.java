package com.lee.persistence.json;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author KitenLee
 * * @date 2026/1/27
 */
public abstract class JsonRepository<T,ID> {
    protected final JsonFileStore store;
    protected final Class<T> entityType;

    protected JsonRepository(JsonFileStore store, Class<T> entityType) {
        this.store = store;
        this.entityType = entityType;
    }

    protected abstract ID getId(T entity);

    public List<T> findAll() {
        return store.readList(entityType);
    }

    public Optional<T> findById(ID id) {
        return findAll().stream().filter(e -> Objects.equals(getId(e), id)).findFirst();
    }

    public void save(T entity) {
        List<T> list = findAll();
        ID id = getId(entity);
        list.removeIf(e -> Objects.equals(getId(e), id));
        list.add(entity);
        store.writeList(list);
    }

    public void deleteById(ID id) {
        List<T> list = findAll();
        list.removeIf(e -> Objects.equals(getId(e), id));
        store.writeList(list);
    }
}
