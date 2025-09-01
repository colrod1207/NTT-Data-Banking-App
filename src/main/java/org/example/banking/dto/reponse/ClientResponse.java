package org.example.banking.dto.reponse;

public class ClientResponse {
    private Long id;
    private String dni;
    private String firstName;
    private String lastName;
    private String email;

    public ClientResponse(Long id, String dni, String firstName, String lastName, String email) {
        this.id = id;
        this.dni = dni;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getDni() { return dni; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
}
