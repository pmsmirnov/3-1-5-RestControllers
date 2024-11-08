package ru.pmsmirnov.springsecurity.securityApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pmsmirnov.springsecurity.securityApp.models.CrudRole;


import java.util.List;

@Repository
public interface CrudRoleDao extends JpaRepository<CrudRole, Integer> {

    List<CrudRole> findAll();

    CrudRole findByRoleName(String roleName);

}
