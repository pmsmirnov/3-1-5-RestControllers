package ru.pmsmirnov.springsecurity.securityApp.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pmsmirnov.springsecurity.securityApp.dao.CrudUserDao;
import ru.pmsmirnov.springsecurity.securityApp.models.CrudUser;
import ru.pmsmirnov.springsecurity.securityApp.security.CrudUserDetails;

import java.util.Optional;

@Service
public class CrudUserDetailsService implements UserDetailsService {


    private CrudUserDao crudUserDao;

    @Autowired
    public void setCrudUserDao(CrudUserDao crudUserDao) {
        this.crudUserDao = crudUserDao;
    }

    public CrudUserDetailsService() {

    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {
        Optional<CrudUser> crudUserOptional = crudUserDao.findByNickName(nick);
        if (crudUserOptional.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь с таким ником не найден");
        }
        return new CrudUserDetails(crudUserOptional.get());
   }

}
