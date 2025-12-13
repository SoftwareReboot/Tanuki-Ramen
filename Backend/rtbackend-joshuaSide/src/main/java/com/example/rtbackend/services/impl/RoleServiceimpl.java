package com.example.rtbackend.services.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rtbackend.domain.entities.Role;
import com.example.rtbackend.repo.RoleRepo;
import com.example.rtbackend.services.RoleService;

@Service
@Transactional
public class RoleServiceimpl implements RoleService { 

    private final RoleRepo roleRepo;

    
    public RoleServiceimpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Role not found with ID: " + id));
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepo.findByRoleName(roleName)
                .orElseThrow(() -> new NoSuchElementException("Role not found with name: " + roleName));
    }

    @Override
    public Role createRole(Role role) {
        if (roleRepo.findByRoleName(role.getRoleName()).isPresent()) {
            throw new IllegalArgumentException("Role with name " + role.getRoleName() + " already exists.");
        }
        return roleRepo.save(role);
    }

    @Override
    public Role updateRole(Long id, Role role) {
        Role existingRole = roleRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Role not found with ID: " + id));

        if (!existingRole.getRoleName().equals(role.getRoleName()) &&
                roleRepo.findByRoleName(role.getRoleName()).isPresent()) {
            throw new IllegalArgumentException("Role with name " + role.getRoleName() + " already exists.");
        }

        existingRole.setRoleName(role.getRoleName());
        return roleRepo.save(existingRole);
    }

    @Override
    public void deleteRole(Long id) {
        if (!roleRepo.existsById(id)) {
            throw new NoSuchElementException("Cannot delete. Role not found with ID: " + id);
        }
        roleRepo.deleteById(id);
    }
}