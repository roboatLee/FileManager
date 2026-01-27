package com.lee.service.impl;

import com.lee.dao.file.favorite.FavoriteFolderDto;
import com.lee.dao.file.favorite.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/1/27
 */
@Service
public class FavoriteServiceImpl {
    private final FavoriteRepository repository;

    @Autowired
    public FavoriteServiceImpl(FavoriteRepository repository) {
        this.repository = repository;
    }

    public List<FavoriteFolderDto> listFavorites() {
        return repository.findAll();
    }

    public void addFavorite(FavoriteFolderDto folder) {
        if (!repository.existsByPath(folder.getPath())) {
            repository.save(folder);
        }
    }

    public void removeFavorite(String id) {
        repository.deleteById(id);
    }
}
