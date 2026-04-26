package io.github.wielkikuba.personalfinance.transaction;

import io.github.wielkikuba.personalfinance.category.Category;
import io.github.wielkikuba.personalfinance.category.CategoryService;
import io.github.wielkikuba.personalfinance.transaction.dto.CreateTransactionRequest;
import io.github.wielkikuba.personalfinance.transaction.dto.UpdateTransactionRequest;
import io.github.wielkikuba.personalfinance.user.User;
import io.github.wielkikuba.personalfinance.user.UserService;
import io.github.wielkikuba.personalfinance.transaction.dto.UserTransactionSummary;
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
    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody CreateTransactionRequest request) {
        Category category = categoryService.getCategoryById(request.getCategoryId());
        User user = userService.getUserById(request.getUserId());

        Transaction transaction = transactionService.createTransaction(
                request.getAmount(), request.getDate(), request.getTransactionType(), category, user
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id){
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactionList(){
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/house/{houseId}/summary")
    public ResponseEntity<List<Transaction>> getSummaryByHouse(@PathVariable Long houseId){
        return ResponseEntity.ok(transactionService.getSummaryByHouse(houseId));
    }

    @GetMapping("/user/{userId}/summary")
    public ResponseEntity<UserTransactionSummary> getUserSummary(@PathVariable Long userId){
        return ResponseEntity.ok(transactionService.getUserSummary(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Transaction>> searchUserTransactions(
            @RequestParam Long userId,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) Long categoryId) {

        List<Transaction> results = transactionService.searchTransactions(
                userId, minAmount, maxAmount, startDate, endDate, type, categoryId);

        return ResponseEntity.ok(results);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Transaction> modifyTransaction(@RequestBody UpdateTransactionRequest transactionRequest,@PathVariable Long id){
        return ResponseEntity.ok(
                transactionService.modifyTransaction(
                        id,
                        transactionRequest.getTransactionType(),
                        transactionRequest.getDate(),
                        transactionRequest.getAmount(),
                        transactionRequest.getCategoryId(),
                        transactionRequest.getUserId()
                )
        );
    }
}