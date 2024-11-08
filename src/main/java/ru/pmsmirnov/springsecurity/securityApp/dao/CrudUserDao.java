package ru.pmsmirnov.springsecurity.securityApp.dao;

import ru.pmsmirnov.springsecurity.securityApp.models.CrudUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CrudUserDao extends JpaRepository<CrudUser, Integer> {

    Optional<CrudUser> findByNickName(String nick);

    List<CrudUser> findAll();

    CrudUser save (CrudUser entity);

    Optional<CrudUser> findById(int id);

    @Override
    void delete(CrudUser entity);

}
