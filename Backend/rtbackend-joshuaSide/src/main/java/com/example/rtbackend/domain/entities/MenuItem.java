package com.example.rtbackend.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "menu_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_item_id", nullable = false, updatable = false)
    private Long menuItemId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(columnDefinition = "TEXT", nullable = false , updatable = true)
    private String description;
    
    @Column(updatable = false, nullable = false)
    private String category;
    
    @Column(name = "image_path")
    private String imagePath;
    
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity = 0;
}
