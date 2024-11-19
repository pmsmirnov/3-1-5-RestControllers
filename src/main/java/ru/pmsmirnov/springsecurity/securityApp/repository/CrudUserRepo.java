package ru.pmsmirnov.springsecurity.securityApp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.pmsmirnov.springsecurity.securityApp.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrudUserRepo extends JpaRepository<User, Integer> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByNickName(String nick);

    List<User> findAll();

    User save(User entity);

    Optional<User> findById(int id);

    @Override
    void delete(User entity);


    @Procedure(name = "addUser")
    void addUserOnStart(@Param("firstName") String firstName, @Param("lastName") String lastName,
                       @Param("eMail") String eMail, @Param("password") String password,
                       @Param("nickNameIn") String nickNameIn, @Param("roleOfUser") String roleOfUser);

    @Procedure(name = "cleanAllTables")
    void cleanAllTables();

}
