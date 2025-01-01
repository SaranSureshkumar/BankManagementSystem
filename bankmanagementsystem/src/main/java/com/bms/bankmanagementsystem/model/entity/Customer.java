package com.bms.bankmanagementsystem.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Customer")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Customer_ID")
    int customerId;

    @Column(name = "First_Name")
    String firstName;

    @Column(name = "Last_Name")
    String lastName;

    @Column(name = "Email")
    String email;

    @Column(name = "Phone_Number")
    String phoneNumber;

    @Column(name = "Password")
    String password;

    @Column(name = "Address")
    String address;

    @Column(name = "City")
    String city;

    @Column(name = "State")
    String state;

    @Column(name = "Postal_Code")
    String postalCode;

    @Column(name = "Country")
    String country;

    @Column(name = "Created_At")
    String createdAt;

    @Column(name = "Updated_At")
    String updatedAt;

    @Column(name = "Customer_Key")
    String customerKey;
}
