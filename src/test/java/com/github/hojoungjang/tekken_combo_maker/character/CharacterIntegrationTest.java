package com.github.hojoungjang.tekken_combo_maker.character;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CharacterIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("[GET characters] 캐릭터 목록 조회 API 호출")
    @Test
    public void getCharacters() throws Exception {
        mockMvc.perform(get("/api/v1/characters")
                .param("size", "3")
                .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.page.size").value(3))
                .andExpect(jsonPath("$.data.page.number").value(1));
    }
}
