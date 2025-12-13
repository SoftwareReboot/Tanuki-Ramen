package com.example.rtbackend.services;

import java.util.List;

import com.example.rtbackend.domain.entities.Role;

public interface RoleService {
    List<Role> getAllRoles();

    Role getRoleById(Long id);

    Role getRoleByName(String roleName);

    Role createRole(Role role);

    Role updateRole(Long id, Role role);

    void deleteRole(Long id);
}
