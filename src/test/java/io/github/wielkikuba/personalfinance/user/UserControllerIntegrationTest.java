package io.github.wielkikuba.personalfinance.user;

import io.github.wielkikuba.personalfinance.house.House;
import io.github.wielkikuba.personalfinance.house.HouseRepository;
import io.github.wielkikuba.personalfinance.transaction.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void clean() {
        transactionRepository.deleteAll();
        houseRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateUser() throws Exception {
        String jsonPayload = """
                {
                    "name": "Jan",
                    "surname": "Testowy"
                }
                """;

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jan"))
                .andExpect(jsonPath("$.surname").value("Testowy"));
        userRepository.flush();
        assertThat(userRepository.findAll()).hasSize(1);
    }

    @Test
    void shouldAssignUserToHouse() throws Exception {
        // GIVEN
        User owner = userRepository.save(User.builder().name("Wlasciciel").surname("Domu").build());
        User member = userRepository.save(User.builder().name("Zwykly").surname("Domownik").build());
        House house = houseRepository.save(House.builder().street("Testowa").number("1").owner(owner).build());

        String jsonPayload = """
                {
                    "userId": %d,
                    "houseId": %d,
                    "userToModifyId": %d
                }
                """.formatted(owner.getId(), house.getId(), member.getId());

        // WHEN & THEN
        mockMvc.perform(post("/api/user/assign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());

        // Z bazy wyciągamy domownika i sprawdzamy, czy został zameldowany
        User updatedMember = userRepository.findById(member.getId()).orElseThrow();
        assertThat(updatedMember.getHouse().getId()).isEqualTo(house.getId());
    }

    @Test
    void shouldRejectAssigningUserByNonOwner() throws Exception {
        // GIVEN: Ktoś inny próbuje dodać kogoś do domu
        User owner = userRepository.save(User.builder().name("Wlasciciel").surname("Wlasciciel surname").build());
        User hacker = userRepository.save(User.builder().name("Haker").surname("Haker surname").build());
        User member = userRepository.save(User.builder().name("Domownik").surname("Domownik surname").build());
        House house = houseRepository.save(House.builder().street("Testowa").number("1").owner(owner).build());

        String jsonPayload = """
                {
                    "userId": %d,
                    "houseId": %d,
                    "userToModifyId": %d
                }
                """.formatted(hacker.getId(), house.getId(), member.getId());

        // WHEN & THEN: Oczekujemy błędu 403 Forbidden!
        mockMvc.perform(post("/api/user/assign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isForbidden());
    }
}