package com.transgressoft.csbe.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith (SpringExtension.class)
@AutoConfigureMockMvc
class ProcessStarterEndpointTest {

    @Inject
    MockMvc mockMvc;
    @Inject
    ObjectMapper objectMapper;

    @Test
    @DisplayName ("Synchronous process start")
    void synchronousProcessStartTest() throws Exception {
        String bodyString = objectMapper.writeValueAsString(ImmutableMap.<String, Object>builder()
                                                             .put("var1", "var1value")
                                                             .build());

        mockMvc.perform(post("/start/synchronous_echo_test/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bodyString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.businessKey", notNullValue()))
                .andExpect(jsonPath("$.processDefinitionId", notNullValue()))
                .andExpect(jsonPath("$.isEnded", is(true)))
                .andExpect(jsonPath("$.variables.var1", is("var1value")));
    }

    @Test
    @DisplayName ("Coalesce function in decision table process")
    void coalesceFunctionDecisionProcessTest() throws Exception {
        mockMvc.perform(post("/start/coalesce_process/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.businessKey", notNullValue()))
                .andExpect(jsonPath("$.processDefinitionId", notNullValue()))
                .andExpect(jsonPath("$.isEnded", is(true)))
                .andExpect(jsonPath("$.variables.result", is(10)));
    }
}