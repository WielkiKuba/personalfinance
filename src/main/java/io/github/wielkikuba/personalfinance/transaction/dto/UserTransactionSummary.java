package io.github.wielkikuba.personalfinance.transaction.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserTransactionSummary {
    private BigDecimal totalIncome;
    private BigDecimal totalOutcome;
    private BigDecimal balance;
}
