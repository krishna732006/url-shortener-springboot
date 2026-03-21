package com.url.shortner.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;
    private String emailId;
    private String userName;
    private String password;
    private String Role = "Role_User";


}
