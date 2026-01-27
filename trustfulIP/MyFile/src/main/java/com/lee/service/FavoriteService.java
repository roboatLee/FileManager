package com.lee.service;

import com.lee.dao.file.favorite.FavoriteFolderDto;
import com.lee.dao.file.favorite.FavoriteRepository;

import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/1/27
 */
public interface FavoriteService {
    List<FavoriteFolderDto> listFavorites();
    void addFavorite(FavoriteFolderDto folder);
    void removeFavorite(String id);
}
