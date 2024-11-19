package ru.pmsmirnov.springsecurity.securityApp.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pmsmirnov.springsecurity.securityApp.repository.CrudUserRepo;
import ru.pmsmirnov.springsecurity.securityApp.models.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final CrudUserRepo userDao;

    @Autowired
    public UserServiceImpl(CrudUserRepo userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {
        Optional<User> crudUserOptional = userDao.findByNickName(nick);
        if (crudUserOptional.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь с таким ником не найден");
        }
        return crudUserOptional.get();
    }

    @Transactional
    @Override
    public void add(User user) {
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        List<User> userList = userDao.findAll();
        userList.stream().forEach(x -> Hibernate.initialize(x.getRoles()));
        return userDao.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(int id) {
        if ((userDao.findById(id)).isEmpty())
            return null;
        return userDao.findById(id).get();
    }

    @Transactional
    public void update(User user) {
        userDao.save(user);
    }

    @Transactional
    public void deleteUser(User user) {
        userDao.delete(user);
    }

    @Transactional(readOnly = true)
    public User getCrudUserByName(String nick) {
        return userDao.findByNickName(nick).get();
    }

}
