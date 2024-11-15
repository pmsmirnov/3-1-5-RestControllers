package ru.pmsmirnov.springsecurity.securityApp.dto;

import ru.pmsmirnov.springsecurity.securityApp.models.Role;
import ru.pmsmirnov.springsecurity.securityApp.models.User;

import java.util.List;
import java.util.Set;


public class UserDTO {

    final static String defaultPassword = "password";
    private int id;
    private String nick;
    private String firstName;
    private String lastName;
    private String eMail;
    private List<String> rolesList;
    private String password;

    public UserDTO() {

    }
    public UserDTO(User user) {
        id = user.getId();
        nick = user.getUsername();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        eMail = user.geteMail();
        rolesList = user.getRoles().stream().map(Role::getTrimName).toList();
        password = defaultPassword;
    }

    public static User userDTOToUser (UserDTO userDTO, Set<Role> roleSet, String password) {
        User user = new User();
        user.setNickName(userDTO.getNick());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.seteMail(userDTO.geteMail());
        user.setPasswd(password);
        user.setRoles(roleSet);
        return user;
    }

    public static User updateUser (UserDTO userDTO, User user, Set<Role> roleSet, String password) {
        user.setNickName(userDTO.getNick());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.seteMail(userDTO.geteMail());
        user.setPasswd(password);
        user.setRoles(roleSet);
        return user;
    }

    public List<String> getRolesList() {
        return rolesList;
    }

    public int getId() {
        return id;
    }

    public String getNick() {
        return nick;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setRolesList(List<String> rolesList) {
        this.rolesList = rolesList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", nick='" + nick + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", eMail='" + eMail + '\'' +
                ", rolesList=" + rolesList +
                ", password='" + password + '\'' +
                '}';
    }
}
