package com.example.simpleProject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firstName",length = 255)
    private String firstName;
    @Column(name = "lastName",length = 255)
    private String lastName;
    @Column(name = "email",length = 255)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "address",length = 255)
    private String address;


    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
            this.address = address;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id){
        this.id=id;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public void setFirstName(String firstName){
        this.firstName=firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public void setLastName(String lastName){
        this.lastName=lastName;
    }
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address=address;
    }
}


