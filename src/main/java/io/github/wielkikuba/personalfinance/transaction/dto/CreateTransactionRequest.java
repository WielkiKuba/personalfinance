package io.github.wielkikuba.personalfinance.transaction.dto;

import io.github.wielkikuba.personalfinance.transaction.TransactionCategory;
import io.github.wielkikuba.personalfinance.transaction.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateTransactionRequest {
    @NotNull(message = "Amount of transaction cannot be null")
    private BigDecimal amount;
    @NotNull(message = "Date of transaction cannot be null")
    private LocalDate date;
    @NotNull(message = "type of transaction cannot be null")
    private TransactionType transactionType;
    @NotNull(message = "category of transaction cannot be null")
    private TransactionCategory transactionCategory;
    @NotNull(message = "User id cannot be null")
    private Long userId;
}
