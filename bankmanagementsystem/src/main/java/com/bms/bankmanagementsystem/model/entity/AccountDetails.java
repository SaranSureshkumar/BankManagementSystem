package com.bms.bankmanagementsystem.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AccountDetails")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetails {

    @Id
    private Integer customerId;

    private Boolean savingsAccount = false;

    private Boolean loanAccount = false;

    private Boolean dematAccount = false;

    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
