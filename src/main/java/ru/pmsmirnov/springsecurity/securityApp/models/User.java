package ru.pmsmirnov.springsecurity.securityApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "crud_users")

@NamedStoredProcedureQuery(name = "addUser",
        procedureName = "addUser", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "firstName", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "lastName", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "eMail", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "password", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "nickNameIn", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "roleOfUser", type = String.class)
})

public class User implements UserDetails {

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


    @Column(name = "passwd")
    private String passwd;

    @ManyToMany (fetch = FetchType.LAZY)
    private Set<Role> roles;

    public User() {
    }

    public User(int id, String nickName, String firstName, String lastName,
                String eMail, String passwd, Set<Role> roles) {
        this.id = id;
        this.nickName = nickName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.passwd = passwd;
        this.roles = roles;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @JsonIgnore
    public Set<Role> getRoles() {
        return roles;
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

    public String getUsername() {
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

    @JsonIgnore
    public String getPassword() {
        return passwd;
    }

    public void setPasswd(String password) {
        this.passwd = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", eMail='" + eMail + '\'' +
                ", passwd='" + passwd + '\'' +
                ", roles=" + roles +
                '}';
    }
}
