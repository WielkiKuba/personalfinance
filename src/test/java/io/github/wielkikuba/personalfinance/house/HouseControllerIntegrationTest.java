package io.github.wielkikuba.personalfinance.house;

import io.github.wielkikuba.personalfinance.transaction.TransactionRepository;
import io.github.wielkikuba.personalfinance.user.User;
import io.github.wielkikuba.personalfinance.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class HouseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void clean() {
        transactionRepository.deleteAll();
        houseRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateHouseAndAssignOwner() throws Exception {
        // GIVEN
        User owner = userRepository.save(User.builder().name("Jan").surname("Budowniczy").build());

        String jsonPayload = """
                {
                    "street": "Dębowa",
                    "number": "5B",
                    "owner_id": %d
                }
                """.formatted(owner.getId());

        // WHEN
        mockMvc.perform(post("/api/house")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.street").value("Dębowa"))
                .andExpect(jsonPath("$.number").value("5B"));

        // THEN
        assertThat(houseRepository.findAll()).hasSize(1);
    }

    @Test
    void shouldGetHouseByStreetAndNumber() throws Exception {
        // GIVEN
        User owner = userRepository.save(User.builder().name("Wlasciciel").surname("Wlasciciel surname").build());
        houseRepository.save(House.builder().street("Kwiatowa").number("15").owner(owner).build());

        // WHEN & THEN
        mockMvc.perform(get("/api/house/streetAndNumber/Kwiatowa/15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("Kwiatowa"))
                .andExpect(jsonPath("$.number").value("15"));
    }
}