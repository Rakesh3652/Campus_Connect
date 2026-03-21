package com.campusconnect.model;

<<<<<<< HEAD
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

=======
>>>>>>> 7c6335e2c3eee85c71b14ed54fe5b7a2f5bec643
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
<<<<<<< HEAD
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
=======
>>>>>>> 7c6335e2c3eee85c71b14ed54fe5b7a2f5bec643
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
<<<<<<< HEAD
@Table(name = "users")
=======
>>>>>>> 7c6335e2c3eee85c71b14ed54fe5b7a2f5bec643
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
<<<<<<< HEAD

    private String name;

    @NotBlank
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String phoneNumber;
    private String fullName;

    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

    @OneToMany
    private Set<Venue> venues = new HashSet<>();

    
=======
    private String name;
    private String email;
    private String password;
    private String PhoneNumber;
    private String UserName;    
>>>>>>> 7c6335e2c3eee85c71b14ed54fe5b7a2f5bec643
}
