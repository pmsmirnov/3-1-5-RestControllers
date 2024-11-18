package ru.pmsmirnov.springsecurity.securityApp.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.pmsmirnov.springsecurity.securityApp.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrudUserDao extends JpaRepository<User, Integer> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByNickName(String nick);

    List<User> findAll();

    User save(User entity);

    Optional<User> findById(int id);

    @Override
    void delete(User entity);



/*  Для MySQL addUserOnStart
    DELIMITER //
    CREATE PROCEDURE addUser(
            IN firstName VARCHAR(30),
    IN lastName VARCHAR(30),
    IN eMail VARCHAR(255),
    IN password varchar(255),
    IN nickNameIn varchar(30),
    IN roleOfUser varchar(30)
    )
    BEGIN
    insert into crud_users (F_Name, L_Name, mail, passwd, nickname)
    values (firstName, lastName, eMail, password, nickNameIn);
    SET @user_id = LAST_INSERT_ID();
    insert into crud_role (crud_role_name)
    values (roleOfUser);
    SET @role_id = LAST_INSERT_ID();
    insert ignore into crud_users_roles (users_id, roles_id) VALUE (@user_id, @role_id);
    END//
    DELIMITER ;
*/

    @Procedure(name = "addUser")
    void addUserOnStart(@Param("firstName") String firstName, @Param("lastName") String lastName,
                       @Param("eMail") String eMail, @Param("password") String password,
                       @Param("nickNameIn") String nickNameIn, @Param("roleOfUser") String roleOfUser);

/*  Для MySQL cleanAllTables
    DELIMITER //
    CREATE PROCEDURE cleanAllTables()
    BEGIN
    SET FOREIGN_KEY_CHECKS = 0;
    TRUNCATE crud_users_roles;
    TRUNCATE crud_users;
    TRUNCATE crud_role;
    SET FOREIGN_KEY_CHECKS = 1;
    END//
    DELIMITER ;
*/
    @Procedure(name = "cleanAllTables")
    void cleanAllTables();

}
