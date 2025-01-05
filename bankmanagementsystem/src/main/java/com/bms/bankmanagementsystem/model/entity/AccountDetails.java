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

    private int savingsAccountCounts = 0;

    private Boolean loanAccount = false;

    private int loanAccountsCount = 0;

    private Boolean dematAccount = false;

    private int dematAccountCount = 0;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId", nullable = false)
    private Customer customer;

}
