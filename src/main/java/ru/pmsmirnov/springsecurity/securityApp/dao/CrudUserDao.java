package ru.pmsmirnov.springsecurity.securityApp.dao;

import org.springframework.data.jpa.repository.Query;
import ru.pmsmirnov.springsecurity.securityApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CrudUserDao extends JpaRepository<User, Integer> {

    Optional<User> findByNickName(String nick);

    List<User> findAll();

    User save (User entity);

    Optional<User> findById(int id);

    @Override
    void delete(User entity);

}
