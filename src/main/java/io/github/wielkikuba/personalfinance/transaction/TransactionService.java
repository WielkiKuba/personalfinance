package io.github.wielkikuba.personalfinance.transaction;

import io.github.wielkikuba.personalfinance.category.Category;
import io.github.wielkikuba.personalfinance.category.CategoryService;
import io.github.wielkikuba.personalfinance.house.House;
import io.github.wielkikuba.personalfinance.house.HouseService;
import io.github.wielkikuba.personalfinance.transaction.dto.UserTransactionSummary;
import io.github.wielkikuba.personalfinance.user.User;
import io.github.wielkikuba.personalfinance.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final HouseService houseService;

    @Transactional
    public Transaction createTransaction(BigDecimal amount, LocalDate date, TransactionType transactionType, Category category, User user){
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .date(date)
                .transactionType(transactionType)
                .category(category)
                .user(user)
                .build();
        return transactionRepository.save(transaction);
    }

    @Transactional
    public void deleteTransaction(Long id){
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Transaction "+id+" does not exist"));
        try{
            transactionRepository.delete(transaction);
            transactionRepository.flush();
        }catch (DataIntegrityViolationException e){
            throw new RuntimeException("Transaction cannot be deleted due to existing constraints.");
        }
    }

    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    @Transactional
    public Transaction modifyTransaction(Long id,TransactionType transactionType,LocalDate date,BigDecimal amount,Long categoryId, Long userId){
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Transaction "+id+ " does not exists"));
        if(transactionType!=null){
            transaction.setTransactionType(transactionType);
        }
        if(date!=null){
            transaction.setDate(date);
        }
        if(amount!=null){
            transaction.setAmount(amount);
        }
        if(categoryId!=null){
            transaction.setCategory(categoryService.getCategoryById(categoryId));
        }
        if(userId!=null){
            transaction.setUser(userService.getUserById(userId));
        }
        return transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public Transaction getTransactionById(Long id){
        return transactionRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Transaction "+id+" does not exist"));
    }

    @Transactional(readOnly = true)
    public List<Transaction> searchTransactions(Long userId, BigDecimal minAmount, BigDecimal maxAmount,LocalDate startDate, LocalDate endDate,TransactionType type, Long categoryId) {
        return transactionRepository.searchTransactions(
                userId, minAmount, maxAmount, startDate, endDate, type, categoryId);
    }

    @Transactional(readOnly = true)
    public List<Transaction> getSummaryByHouse(Long houseId){
        House house = houseService.getHouseById(houseId);
        List<User> userList = userService.getUserListByHouse(house);
        List<Transaction> transactionList = new ArrayList<>();
        for(User user : userList){
            List<Transaction> tempTransactionList = searchTransactions(user.getId(),null,null,null,null,null,null);
            transactionList.addAll(tempTransactionList);
        }
        return transactionList;
    }

    @Transactional(readOnly = true)
    public UserTransactionSummary getUserSummary(Long userId){
        LocalDate date = LocalDate.now();
        LocalDate dateBefore = date.minusMonths(1);
        List<Transaction> transactionList = searchTransactions(userId, null, null, dateBefore, date, null, null);
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalOutcome = BigDecimal.ZERO;
        for(Transaction transaction : transactionList){
            if(transaction.getTransactionType() == TransactionType.INCOME){
                totalIncome = totalIncome.add(transaction.getAmount());
            } else if (transaction.getTransactionType() == TransactionType.EXPENSE) {
                totalOutcome = totalOutcome.add(transaction.getAmount());
            }
        }
        BigDecimal balance = totalIncome.subtract(totalOutcome);
        return UserTransactionSummary.builder().totalIncome(totalIncome).totalOutcome(totalOutcome).balance(balance).build();
    }
}