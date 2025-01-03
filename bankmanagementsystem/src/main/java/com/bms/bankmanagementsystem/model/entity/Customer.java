package com.bms.bankmanagementsystem.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private Integer customerId;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    private String password;

    private String address;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(unique = true)
    private String customerKey;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private AccountDetails accountDetails;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private SavingsAccount savingsAccount;
}
