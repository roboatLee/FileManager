package com.lee.dao.file.favorite;

import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/1/27
 */
public interface FavoriteRepository {
    List<FavoriteFolderDto> findAll();
    void save(FavoriteFolderDto folder);
    void deleteById(String id);
    boolean existsByPath(String path);
}
