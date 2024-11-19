package ru.pmsmirnov.springsecurity.securityApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pmsmirnov.springsecurity.securityApp.models.Role;

import java.util.List;

@Repository
public interface CrudRoleRepo extends JpaRepository<Role, Integer> {

    List<Role> findAll();
    Role findByRoleName(String roleName);

}
