package io.github.wielkikuba.personalfinance.transaction.dto;

import io.github.wielkikuba.personalfinance.transaction.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateTransactionRequest {
    private BigDecimal amount;
    private LocalDate date;
    private TransactionType transactionType;
    private Long categoryId;
    private Long userId;
}
