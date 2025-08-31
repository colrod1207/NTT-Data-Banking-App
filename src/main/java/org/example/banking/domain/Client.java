package org.example.banking.domain;

import javax.persistence.*;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String firstName;
    @Column(nullable = false) private String lastName;
    @Column(nullable = false, unique = true) private String email;
    @Column(nullable = false, unique = true) private String dni;

    protected Client() {}

    public Client(String firstName, String lastName, String email, String dni) {
        if(isBlank(firstName) || isBlank(lastName) || isBlank(email) || isBlank(dni)) {
            throw new IllegalArgumentException("All required fields must be filled");
        }
        if(!email.contains("@")){
            throw new IllegalArgumentException("Email address must be a valid email address");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.email = email;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    // ---Getters---
    public Long getId()
    {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getDni() {
        return dni;
    }

    // ---Setters---
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

}
