package com.college.erp.entity;

import jakarta.persistence.*;

//@Entity tells JPA/Hibernate:"This Java class represents a database table."

@Entity
@Table(name = "users") //By default, Hibernate will use the class name as the table name, but we'll explicitly set it to users:
public class User {

    //What these mean:
    //
    //@Id → this is the primary key
    //@GeneratedValue → database generates the ID automatically
    //IDENTITY → MySQL's auto-increment mechanism
    //Long → Java data type for the ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column tells JPA that we're configuring how this Java field should behave as a database column.
    @Column(nullable = false, unique = true)
    private String email;
    //we will NOT store the user's actual password as plain text.
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public User() {
    }

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
