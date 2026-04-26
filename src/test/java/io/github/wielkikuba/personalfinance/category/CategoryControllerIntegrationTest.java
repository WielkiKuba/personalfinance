package io.github.wielkikuba.personalfinance.category;

import io.github.wielkikuba.personalfinance.house.HouseRepository;
import io.github.wielkikuba.personalfinance.transaction.TransactionRepository;
import io.github.wielkikuba.personalfinance.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clean() {
        transactionRepository.deleteAll();
        categoryRepository.deleteAll();
        houseRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateCategoryViaPath() throws Exception {
        // Kategoria używa zmiennej w ścieżce (Path Variable) do tworzenia
        mockMvc.perform(post("/api/category/name/Jedzenie"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jedzenie"));

        assertThat(categoryRepository.findAll()).hasSize(1);
    }

    @Test
    void shouldReturnAllCategories() throws Exception {
        categoryRepository.save(Category.builder().name("Samochód").build());
        categoryRepository.save(Category.builder().name("Zdrowie").build());

        mockMvc.perform(get("/api/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }
}