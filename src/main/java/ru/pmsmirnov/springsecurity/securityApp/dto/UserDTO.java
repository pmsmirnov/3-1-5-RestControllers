package ru.pmsmirnov.springsecurity.securityApp.dto;

import ru.pmsmirnov.springsecurity.securityApp.models.Role;
import ru.pmsmirnov.springsecurity.securityApp.models.User;

import java.util.List;


public class UserDTO {

    private String defaultPassword = "password";
    private int id;
    private String nick;
    private String firstName;
    private String lastName;
    private String eMail;
    private List<String> rolesList;

    public UserDTO() {

    }
    public UserDTO(User user) {
        id = user.getId();
        nick = user.getUsername();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        eMail = user.geteMail();
        rolesList = user.getRoles().stream().map(Role::getTrimName).toList();
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
        return defaultPassword;
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
        this.defaultPassword = password;
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
                ", password='" + defaultPassword + '\'' +
                '}';
    }
}
