package com.lee.service.impl;

import com.lee.dao.file.favorite.FavoriteFolderDto;
import com.lee.dao.file.favorite.FavoriteRepository;
import com.lee.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/1/27
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository repository;

    @Autowired
    public FavoriteServiceImpl(FavoriteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FavoriteFolderDto> listFavorites() {
        return repository.findAll();
    }

    @Override
    public void addFavorite(FavoriteFolderDto folder) {
        if (!repository.existsByPath(folder.getPath())) {
            repository.save(folder);
        }
    }

    @Override
    public void removeFavorite(String id) {
        repository.deleteById(id);
    }
}
