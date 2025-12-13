package com.example.rtbackend.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.rtbackend.domain.entities.MenuItem;
import com.example.rtbackend.services.FileStorageService;
import com.example.rtbackend.services.MenuItemService;

import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = "*") // e add nya ni sa tanan controllers
public class MenuItemController {

    private final MenuItemService menuItemService;
    private final FileStorageService fileStorageService;

    public MenuItemController(MenuItemService menuItemService, FileStorageService fileStorageService) {
        this.menuItemService = menuItemService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/create/{requesterId}")
    public ResponseEntity<?> create(
        @PathVariable Long requesterId,
        @RequestBody MenuItem menuItem) {
        try {
            MenuItem createdItem = menuItemService.createMenuItem(requesterId, menuItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping(
        value = "/create/with-image/{requesterId}", 
        consumes = "multipart/form-data",
        produces = "application/json"
    )
    public ResponseEntity<?> uploadImage(
        @PathVariable Long requesterId,
        @RequestParam("file") MultipartFile file,
        @RequestParam("menuItem") String menuItemJson
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            String fileName = fileStorageService.storeFile(file);
            
            MenuItem menuItem = new ObjectMapper().readValue(menuItemJson, MenuItem.class);
            MenuItem createdItem = menuItemService.createMenuItemWithImage(requesterId, menuItem, fileName);
            return ResponseEntity.ok(createdItem);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<MenuItem>> getMenu() {
        List<MenuItem> items = menuItemService.getAllMenuItems();
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        try {
            MenuItem item = menuItemService.getMenuItemById(id);
            return ResponseEntity.ok(item);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{requesterId}/{itemId}")
    public ResponseEntity<?> updateItem(
        @PathVariable Long requesterId,
        @PathVariable Long itemId,
        @RequestBody MenuItem menuItem) {
        try {
            MenuItem updatedItem = menuItemService.updateMenuItem(requesterId, itemId, menuItem);
            return ResponseEntity.ok(updatedItem);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PutMapping("/stock/{requesterId}/{itemId}")
    public ResponseEntity<?> updateStock(
        @PathVariable Long requesterId,
        @PathVariable Long itemId,
        @RequestBody Integer newStock) {
        try {
            MenuItem updatedItem = menuItemService.updateStock(requesterId, itemId, newStock);
            return ResponseEntity.ok(updatedItem);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{requesterId}/{itemId}")
    public ResponseEntity<?> deleteItem(
        @PathVariable Long requesterId,
        @PathVariable Long itemId) {    
        try {
            menuItemService.deleteMenuItem(requesterId, itemId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}