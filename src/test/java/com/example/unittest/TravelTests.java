package com.example.unittest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TravelTests {
    @Autowired
    private MockMvc mockMvc;

    public TravelTests() {
    }

    @Test
    public void show() throws Exception {
        String body = """
                {
                    "hotel":[{"city":"Moscow","start_date":"2023-12-10","end_date":"2023-12-12", "max_price":100000, "stars": [5,4], "order":0}],
                 "avia": [{"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-10"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":0},
                \s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"MOW","to":"LED","date":"2023-12-20"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":1},\s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-24"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":2}]
                 }""";
        this.mockMvc.perform(get("/travel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }


    @Test
    public void show_badBodyRequest() throws Exception {
        String body = """
                {
                 "avia": [{"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-10"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":0},
                \s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"MOW","to":"LED","date":"2023-12-20"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":1},\s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-24"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":2}]
                 }""";
        this.mockMvc.perform(get("/travel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void show_badBodyRequest_BadDate() throws Exception {
        String body = """
                {
                    "hotel":[{"city":"Moscow","start_date":"2026-12-10","end_date":"2023-12-12", "max_price":100000, "stars": [5,4], "order":0}],
                 "avia": [{"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-10"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":0},
                \s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"MOW","to":"LED","date":"2023-12-20"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":1},\s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-24"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":2}]
                 }""";
        this.mockMvc.perform(get("/travel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    public void show_badBodyRequest_NoDate() throws Exception {
        String body = """
                {
                    "hotel":[{"city":"Moscow", "end_date":"2023-12-12", "max_price":100000, "stars": [5,4], "order":0}],
                 "avia": [{"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-10"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":0},
                \s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"MOW","to":"LED","date":"2023-12-20"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":1},\s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-24"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":2}]
                 }""";
        this.mockMvc.perform(get("/travel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.isEmptyString())));
    }


    @Test
    @Transactional
    @WithMockUser(username = "misha", password = "q", roles = {"USER", "ADMIN"})
    public void save() throws Exception {
        String body = """
                {
                    "hotel":[{"city":"Moscow","start_date":"2023-12-10","end_date":"2023-12-12", "max_price":100000, "stars": [5,4], "order":0}],
                 "avia": [{"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-10"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":0},
                \s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"MOW","to":"LED","date":"2023-12-20"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":1},\s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-24"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":2}]
                 }""";

        this.mockMvc.perform(post("/travel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @Transactional
    public void save_unAuthorize() throws Exception {
        String body = """
                {
                    "hotel":[{"city":"Moscow","start_date":"2023-12-10","end_date":"2023-12-12", "max_price":100000, "stars": [5,4], "order":0}],
                 "avia": [{"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-10"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":0},
                \s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"MOW","to":"LED","date":"2023-12-20"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":1},\s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-24"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":2}]
                 }""";
        this.mockMvc.perform(post("/travel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(username = "misha", password = "q", roles = {"USER", "ADMIN"})
    public void save_badBodyRequest() throws Exception {
        String body = """
                {
                 "avia": [{"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-10"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":0},
                \s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"MOW","to":"LED","date":"2023-12-20"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":1},\s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-24"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":2}]
                 }""";
        this.mockMvc.perform(post("/travel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithMockUser(username = "misha", password = "q", roles = {"USER", "ADMIN"})
    public void save_badBodyRequest_BadDate() throws Exception {
        String body = """
                {
                    "hotel":[{"city":"Moscow","start_date":"2026-12-10","end_date":"2023-12-12", "max_price":100000, "stars": [5,4], "order":0}],
                 "avia": [{"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-10"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":0},
                \s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"MOW","to":"LED","date":"2023-12-20"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":1},\s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-24"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":2}]
                 }""";
        this.mockMvc.perform(post("/travel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    @WithMockUser(username = "misha", password = "q", roles = {"USER", "ADMIN"})
    public void save_badBodyRequest_NoDate() throws Exception {
        String body = """
                {
                    "hotel":[{"city":"Moscow","end_date":"2023-12-12", "max_price":100000, "stars": [5,4], "order":0}],
                 "avia": [{"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-10"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":0},
                \s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"MOW","to":"LED","date":"2023-12-20"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":1},\s
                 {"cabin":"F","passengers":{"adults":1,"children":0,"infants":0},"segments":[{"from":"LED","to":"MOW","date":"2023-12-24"}], "isCharter":[true, false], "isBaggage":[true, false], "isHandBaggage":[true, false], "isSkiEquipment":[true, false], "maxPrice":100000, "order":2}]
                 }""";
        this.mockMvc.perform(post("/travel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.isEmptyString())));
    }

    @Test
    @Transactional
    @WithMockUser(username = "misha", password = "q", roles = {"ADMIN"})
    public void remove() throws Exception {
        this.mockMvc.perform(delete("/travel")
                        .param("user_id", "8394fa8c-6687-4aaf-ac51-d8d4407be190"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @WithMockUser(roles = {"USER", "ANONYMOUS"})
    public void remove_unAuthorize() throws Exception {
        this.mockMvc.perform(delete("/travel")
                        .param("user_id", "8394fa8c-6687-4aaf-ac51-d8d4407be190"))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(username = "misha", password = "q", roles = {"ADMIN"})
    public void remove_noParam() throws Exception {
        this.mockMvc.perform(delete("/travel"))
                .andExpect(jsonPath("$.error", is("Неправильное значение параметра. Проверьте передается ли оно")))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithMockUser(username = "misha", password = "q", roles = {"ADMIN"})
    public void remove_invalidParam() throws Exception {
        this.mockMvc.perform(delete("/travel")
                        .param("user_id", "123"))
                .andExpect(jsonPath("$.error", is("Неправильное значение параметра")))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "misha", password = "q", roles = {"USER", "ADMIN"})
    public void get_method() throws Exception {
        this.mockMvc.perform(get("/travel/basket"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void get_method_unAuthorize() throws Exception {
        this.mockMvc.perform(get("/travel/basket"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "misha", password = "q", roles = {"ADMIN"})
    public void getTravelers() throws Exception {
        this.mockMvc.perform(get("/travel/user/basket"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getTravelers_unAuthorize() throws Exception {
        this.mockMvc.perform(get("/travel/user/basket"))
                .andExpect(status().isForbidden());
    }
}
