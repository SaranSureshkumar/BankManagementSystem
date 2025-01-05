package com.bms.bankmanagementsystem.model.request;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GetTransactionDetails {

    String savingsAccountNumber;

    int pageNumber;

    int pageSize;

    String transactionType;

    String dateFrom;

    String dateTo;
}
