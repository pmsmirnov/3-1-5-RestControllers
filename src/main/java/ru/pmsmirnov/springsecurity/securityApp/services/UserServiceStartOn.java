package ru.pmsmirnov.springsecurity.securityApp.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pmsmirnov.springsecurity.securityApp.repository.CrudUserRepo;
import ru.pmsmirnov.springsecurity.securityApp.models.Role;
import ru.pmsmirnov.springsecurity.securityApp.models.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceStartOn {
    private final CrudUserRepo userDao;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceStartOn(CrudUserRepo userDao, RoleService roleService, ApplicationContext context) {

        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = context.getBean(PasswordEncoder.class);
    }

    @PostConstruct
    public void putDataInDbOnStart() {
        String startPassword = passwordEncoder.encode("123");
        userDao.cleanAllTables();
        userDao.addUserOnStart("Админ", "Админов", "admin@mail.ru",
                startPassword, // пароль 123
                "admin", "ROLE_ADMIN");
        userDao.addUserOnStart("Юзер", "Юзеров", "user@mail.ru",
                startPassword,   //пароль 123
                "user", "ROLE_USER");
        userDao.addUserOnStart("API", "API", "api@mail.ru",
                startPassword,   //пароль 123
                "api", "ROLE_API");
        Set<Role> rolesSet = new HashSet<>();
        rolesSet.add(roleService.getRoleByName("ROLE_ADMIN"));
        rolesSet.add(roleService.getRoleByName("ROLE_USER"));
        userDao.save(new User("useradmin","useradmin","useradmin", "useradmin@mail.ru", startPassword, rolesSet));
    }

}
