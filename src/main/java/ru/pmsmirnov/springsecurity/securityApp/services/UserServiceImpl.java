package ru.pmsmirnov.springsecurity.securityApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pmsmirnov.springsecurity.securityApp.dao.CrudUserDao;
import ru.pmsmirnov.springsecurity.securityApp.models.CrudUser;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final CrudUserDao userDao;
    @Autowired
    public UserServiceImpl(CrudUserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void add(CrudUser user) {
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CrudUser> listUsers() {
        return userDao.findAll();
    }

    @Transactional(readOnly = true)
    public CrudUser getUserById(int id) {
        if ((userDao.findById(id)).isEmpty())
            return null;
        return userDao.findById(id).get();
    }

    @Transactional
    public void update(CrudUser user) {
        userDao.save(user);
    }

    @Transactional
    public void deleteUser(CrudUser user) {
        userDao.delete(user);
    }

    @Transactional
    public CrudUser getCrudUserByName(String nick) {
        return userDao.findByNickName(nick).get();
    }

}
