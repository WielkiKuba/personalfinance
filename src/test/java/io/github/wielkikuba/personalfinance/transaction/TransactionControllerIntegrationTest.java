package io.github.wielkikuba.personalfinance.transaction;

import io.github.wielkikuba.personalfinance.category.Category;
import io.github.wielkikuba.personalfinance.category.CategoryRepository;
import io.github.wielkikuba.personalfinance.user.User;
import io.github.wielkikuba.personalfinance.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void shouldCreateTransaction() throws Exception {
        // GIVEN
        User user = userRepository.save(User.builder().name("Test").surname("User").build());
        Category category = categoryRepository.save(Category.builder().name("Spożywcze").build());

        String jsonPayload = """
                {
                    "amount": 150.50,
                    "date": "2026-04-26",
                    "transactionType": "EXPENSE",
                    "categoryId": %d,
                    "userId": %d
                }
                """.formatted(category.getId(), user.getId());

        // WHEN & THEN
        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(150.50))
                .andExpect(jsonPath("$.transactionType").value("EXPENSE"));
    }

    @Test
    void shouldReturnUserSummary() throws Exception {
        // GIVEN: Wrzucamy transakcje wprost do bazy
        User user = userRepository.save(User.builder().name("Jan").surname("Kowalski").build());
        Category category = categoryRepository.save(Category.builder().name("Różne").build());

        LocalDate today = LocalDate.now();

        // Dodajemy 2x Przychód (1000 + 500) i 1x Wydatek (200)
        transactionRepository.save(Transaction.builder().amount(new BigDecimal("1000.00")).date(today).transactionType(TransactionType.INCOME).category(category).user(user).build());
        transactionRepository.save(Transaction.builder().amount(new BigDecimal("500.00")).date(today).transactionType(TransactionType.INCOME).category(category).user(user).build());
        transactionRepository.save(Transaction.builder().amount(new BigDecimal("200.00")).date(today).transactionType(TransactionType.EXPENSE).category(category).user(user).build());

        // WHEN & THEN: Wywołujemy endpoint podsumowania
        mockMvc.perform(get("/api/transactions/user/" + user.getId() + "/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalIncome").value(1500.0))
                .andExpect(jsonPath("$.totalOutcome").value(200.0))
                .andExpect(jsonPath("$.balance").value(1300.0));
    }
}