package com.lee.controller;

import com.lee.dao.file.favorite.FavoriteFolderDto;
import com.lee.service.FavoriteService;
import com.lee.service.impl.FavoriteServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/2/5
 */
@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {
    private final FavoriteServiceImpl service;

    public FavoriteController(FavoriteServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public List<FavoriteFolderDto> list() {
        return service.listFavorites();
    }

    @PostMapping
    public void add(@RequestBody FavoriteFolderDto dto) {
        service.addFavorite(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.removeFavorite(id);
    }
}
