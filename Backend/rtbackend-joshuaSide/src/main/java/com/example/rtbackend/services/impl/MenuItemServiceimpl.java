package com.example.rtbackend.services.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.rtbackend.domain.entities.MenuItem;
import com.example.rtbackend.domain.entities.Role;
import com.example.rtbackend.domain.entities.User;
import com.example.rtbackend.repo.MenuItemRepo;
import com.example.rtbackend.services.MenuItemService;
import com.example.rtbackend.services.RoleService;
import com.example.rtbackend.services.UserService;

@Service
public class MenuItemServiceimpl implements MenuItemService {
    
    private final MenuItemRepo menuItemRepo;
    private final UserService userService;
    private final RoleService roleService;

    private static final String MANAGER_ROLE_ID = "MANAGER";

    public MenuItemServiceimpl(MenuItemRepo menuItemRepo, UserService userService, RoleService roleService) {
        this.menuItemRepo = menuItemRepo;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepo.findAll();
    }

    @Override 
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Menu Item not found with id: " + id));
    }

    @Override
    public MenuItem createMenuItem(Long requesterId, MenuItem item) {
        validateManager(requesterId);
        return menuItemRepo.save(item);
    }

    @Override
    public MenuItem createMenuItemWithImage(Long requesterId, MenuItem menuItem, String imagePath) {
        validateManager(requesterId);
        menuItem.setImagePath(imagePath);
        return menuItemRepo.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(Long requesterId, Long itemId, MenuItem newItem) {
        validateManager(requesterId);
        MenuItem item = getMenuItemById(itemId);

        item.setName(newItem.getName());
        item.setPrice(newItem.getPrice());
        item.setCategory(newItem.getCategory());
        item.setDescription(newItem.getDescription());
        item.setImagePath(newItem.getImagePath());
        item.setStockQuantity(newItem.getStockQuantity());

        return menuItemRepo.save(item);
    }

    @Override
    public MenuItem updateMenuItemWithImage(Long requesterId, Long itemId, MenuItem newItem, String imagePath) {
        validateManager(requesterId);
        MenuItem item = getMenuItemById(itemId);

        item.setName(newItem.getName());
        item.setPrice(newItem.getPrice());
        item.setCategory(newItem.getCategory());
        item.setDescription(newItem.getDescription());
        item.setStockQuantity(newItem.getStockQuantity());
        
        // Only update image path if a new image was uploaded
        if (imagePath != null && !imagePath.isEmpty()) {
            item.setImagePath(imagePath);
        }

        return menuItemRepo.save(item);
    }

    @Override
    public void deleteMenuItem(Long requesterId, Long itemId) {
        validateManager(requesterId);
        menuItemRepo.deleteById(itemId);
    }

    @Override
    public MenuItem updateStock(Long requesterId, Long itemId, int newStock) {
        validateManager(requesterId);
        MenuItem item = getMenuItemById(itemId);
        item.setStockQuantity(newStock);
        return menuItemRepo.save(item);
    }

    private void validateManager(Long userId) {
        User user = userService.getUserById(userId);
        
        if (user.getRole() == null) {
            throw new IllegalStateException("User does not have an assigned role");
        }

        Role managerRole = roleService.getRoleByName(MANAGER_ROLE_ID);
        
        if (!user.getRole().getRoleId().equals(managerRole.getRoleId())) {
            throw new SecurityException("Access denied. Only Managers can perform this action");
        }
    }
}