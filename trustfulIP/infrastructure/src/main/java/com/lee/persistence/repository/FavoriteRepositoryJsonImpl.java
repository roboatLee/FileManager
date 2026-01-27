package com.lee.persistence.repository;

import com.lee.dao.file.favorite.FavoriteFolderDto;
import com.lee.dao.file.favorite.FavoriteRepository;
import com.lee.persistence.json.JsonFileStore;
import com.lee.persistence.json.JsonRepository;
import org.springframework.stereotype.Repository;


import java.nio.file.Paths;

/**
 * @author KitenLee
 * * @date 2026/1/27
 */
@Repository
public class FavoriteRepositoryJsonImpl
        extends JsonRepository<FavoriteFolderDto, String>
        implements FavoriteRepository {

    public FavoriteRepositoryJsonImpl() {
        super(new JsonFileStore(Paths.get("data", "favorites.json")), FavoriteFolderDto.class);
    }

    @Override
    protected String getId(FavoriteFolderDto entity) {
        return entity.getId();
    }

    @Override
    public boolean existsByPath(String path) {
        return findAll().stream().anyMatch(f -> f.getPath().equals(path));
    }
}
