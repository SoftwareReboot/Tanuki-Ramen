package com.example.rtbackend.services;

import java.util.List;

import com.example.rtbackend.domain.entities.MenuItem;

public interface MenuItemService {
    List<MenuItem> getAllMenuItems();

    MenuItem getMenuItemById(Long id);

    MenuItem createMenuItem(Long requesterId, MenuItem menuItem);

    MenuItem createMenuItemWithImage(Long requesterId, MenuItem menuItem, String imagePath);

    MenuItem updateMenuItem(Long requesterId, Long itemId, MenuItem menuItem);

    MenuItem updateMenuItemWithImage(Long requesterId, Long itemId, MenuItem menuItem, String imagePath);

    MenuItem updateStock(Long requesterId, Long itemId, int newStock);
    
    void deleteMenuItem(Long requesterId, Long itemId);
}