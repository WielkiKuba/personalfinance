package io.github.wielkikuba.personalfinance.transaction.dto;

import io.github.wielkikuba.personalfinance.transaction.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateTransactionRequest {
    private TransactionType transactionType;
    private LocalDate date;
    private Long categoryId;
    private Long userId;
    private BigDecimal amount;
}
