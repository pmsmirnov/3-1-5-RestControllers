package ru.pmsmirnov.springsecurity.securityApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "crud_users")
public class CrudUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Поле \"Ник\" не может быть пустым")
    @Size(min = 2, max = 30, message = "Ник дожен быть длиной от 2-х до 30 символов")
    @Column(name = "nickname")
    private String nickName;

    @NotEmpty(message = "Поле \"Имя\" не может быть пустым")
    @Size(min = 2, max = 30, message = "Имя дожно быть длиной от 2-х до 30 символов")
    @Column(name = "F_Name")
    private String firstName;

    @NotEmpty(message = "Поле \"Фамилия\" не может быть пустым")
    @Size(min = 2, max = 30, message = "Фамилия дожна быть длиной от 2-х до 30 символов")
    @Column(name = "L_Name")
    private String lastName;

    @Column(name = "Mail")
    private String eMail;

    @Column(name = "pswd")
    private String pass;

    @ManyToMany (fetch = FetchType.EAGER)
    private Set<CrudRole> roles;

    public CrudUser () {
    }

    public CrudUser(int id, String nickName, String firstName, String lastName, String eMail, String p, Set<CrudRole> r) {
        this.id = id;
        this.nickName = nickName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.pass = p;
        this.roles = r;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String password) {
        this.pass = password;
    }

    public Set<CrudRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<CrudRole> roles) {
        this.roles = roles;
    }
}
