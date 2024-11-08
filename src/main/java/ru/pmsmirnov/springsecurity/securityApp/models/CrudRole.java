package ru.pmsmirnov.springsecurity.securityApp.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "crud_role")
public class CrudRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "crud_role_name")
    private String roleName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<CrudUser> users;

    @Override
    public String getAuthority() {
        return getRoleName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String name) {
        this.roleName = name;
    }

    public Set<CrudUser> getUsers() {
        return users;
    }

    public void setUsers(Set<CrudUser> users) {
        this.users = users;
    }
}
