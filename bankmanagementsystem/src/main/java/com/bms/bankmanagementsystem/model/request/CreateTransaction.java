package com.bms.bankmanagementsystem.model.request;

import com.bms.bankmanagementsystem.utility.helper.TransactionTypeEnum;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CreateTransaction {

    private String customerKey;

    private TransactionTypeEnum transactionType;

    private String transactionFrom;

    private String transactionTo;

    private Double amount;
}
