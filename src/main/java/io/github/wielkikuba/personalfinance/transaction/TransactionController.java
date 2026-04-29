package io.github.wielkikuba.personalfinance.transaction;

import io.github.wielkikuba.personalfinance.transaction.dto.CreateTransactionRequest;
import io.github.wielkikuba.personalfinance.transaction.dto.TransactionDataResponse;
import io.github.wielkikuba.personalfinance.transaction.dto.UpdateTransactionRequest;
import io.github.wielkikuba.personalfinance.user.User;
import io.github.wielkikuba.personalfinance.user.UserService;
import io.github.wielkikuba.personalfinance.transaction.dto.UserTransactionSummary;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<TransactionDataResponse> createTransaction(@Valid @RequestBody CreateTransactionRequest request) {
        User user = userService.getUserById(request.getUserId());

        Transaction transaction = transactionService.createTransaction(
                request.getAmount(), request.getDate(), request.getTransactionType(), request.getTransactionCategory(), user
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionDataResponse.from(transaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id") Long id){
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TransactionDataResponse>> getTransactionList(){
        return ResponseEntity.ok(TransactionDataResponse.from(transactionService.getAllTransactions()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDataResponse> getTransactionById(@PathVariable("id") Long id){
        return ResponseEntity.ok(TransactionDataResponse.from(transactionService.getTransactionById(id)));
    }

    @GetMapping("summary/house/{houseId}")
    public ResponseEntity<List<TransactionDataResponse>> getSummaryByHouse(@PathVariable("houseId") Long houseId){
        return ResponseEntity.ok(TransactionDataResponse.from(transactionService.getSummaryByHouse(houseId)));
    }

    @GetMapping("summary/user/{userId}")
    public ResponseEntity<UserTransactionSummary> getUserSummary(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(transactionService.getUserSummary(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TransactionDataResponse>> searchUserTransactions(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "minAmount", required = false) BigDecimal minAmount,
            @RequestParam(value = "maxAmount", required = false) BigDecimal maxAmount,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "type", required = false) TransactionType type,
            @RequestParam(value = "transactionCategory", required = false) TransactionCategory transactionCategory) {

        List<Transaction> results = transactionService.searchTransactions(
                userId, minAmount, maxAmount, startDate, endDate, type, transactionCategory);

        return ResponseEntity.ok(TransactionDataResponse.from(results));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TransactionDataResponse> modifyTransaction(@RequestBody UpdateTransactionRequest transactionRequest, @PathVariable("id") Long id){
        return ResponseEntity.ok(
                TransactionDataResponse.from(
                    transactionService.modifyTransaction(
                        id,
                        transactionRequest.getTransactionType(),
                        transactionRequest.getDate(),
                        transactionRequest.getAmount(),
                        transactionRequest.getTransactionCategory(),
                        transactionRequest.getUserId()
                    )
                )
        );
    }
}