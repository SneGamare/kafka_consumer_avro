package com.plutus.kotak.commonlibs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plutus.kotak.commonlibs.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void testCreateTransaction() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("fields", new Object[]{
            createField("FORACID", "MTIzNDU2Nzg5MA=="),
            createField("ACCT_NAME", "Sm9obiBEb2U="),
            createField("LAST_TRAN_DATE_CR", "2024-03-20"),
            createField("TRAN_DATE", "2024-03-20"),
            createField("TRAN_ID", "VHJhbnNhY3Rpb24xMjM="),
            createField("PART_TRAN_SRL_NUM", "MTIzNDU="),
            createField("DEL_FLG", "Tg=="),
            createField("TRAN_TYPE", "Q3JlZGl0"),
            createField("TRAN_SUB_TYPE", "VHJhbnNmZXI="),
            createField("VALUE_DATE", "2024-03-20"),
            createField("TRAN_AMT", 1000.50)
        });

        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Transaction created successfully"))
                .andExpect(jsonPath("$.data").exists());
    }

    private Map<String, Object> createField(String name, Object value) {
        Map<String, Object> field = new HashMap<>();
        field.put("name", name);
        field.put("value", value);
        return field;
    }
} 