package io.github.wielkikuba.personalfinance.transaction.dto;

import io.github.wielkikuba.personalfinance.transaction.Transaction;
import io.github.wielkikuba.personalfinance.transaction.TransactionCategory;
import io.github.wielkikuba.personalfinance.transaction.TransactionType;
import io.github.wielkikuba.personalfinance.user.dto.UserDataResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class TransactionDataResponse {

    public static TransactionDataResponse from(Transaction transaction){
        return new TransactionDataResponse(transaction.getId(),transaction.getAmount(),transaction.getDate(),transaction.getTransactionType(),transaction.getTransactionCategory(),UserDataResponse.from(transaction.getUser()));
    }

    public static List<TransactionDataResponse> from(List<Transaction> transactionList){
        List<TransactionDataResponse> transactionDataResponseList = new ArrayList<>();
        for(Transaction transaction : transactionList){
            transactionDataResponseList.add(TransactionDataResponse.from(transaction));
        }
        return transactionDataResponseList;
    }

    private Long id;
    private BigDecimal amount;
    private LocalDate localDate;
    private TransactionType transactionType;
    private TransactionCategory transactionCategory;
    private UserDataResponse userDataResponse;
}
