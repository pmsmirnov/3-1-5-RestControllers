package ru.pmsmirnov.springsecurity.securityApp.mapper;

import ru.pmsmirnov.springsecurity.securityApp.dto.UserDTO;
import ru.pmsmirnov.springsecurity.securityApp.models.Role;
import ru.pmsmirnov.springsecurity.securityApp.models.User;

import java.util.Set;

public class UserMapper {

    public static void userDTOToUser(UserDTO userDTO, User user, Set<Role> roleSet, String password) {
        user.setNickName(userDTO.getNick());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.seteMail(userDTO.geteMail());
        user.setPasswd(password);
        user.setRoles(roleSet);
    }

    public static UserDTO userToUserDTO(User user) {
        return new UserDTO(user);
    }

    public static UserDTO GetAnonymousUserDTO() {
        UserDTO anonymousUserDTO = new UserDTO();
        anonymousUserDTO.setNick("anonymousUser");
        return anonymousUserDTO;
    }


}


