package com.spring.boot.project.demo.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.boot.project.demo.service.ReviewService;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {
    private static final String REQUEST = "/reviews/most-used-words";
    private static final List<String> EXPECTED_MOST_USED_WORDS = List.of("review", "text");
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewService reviewService;

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "USER")
    public void getMostCommentedProduct() throws Exception {
        Mockito.when(reviewService.findMostUsedWords(2)).thenReturn(EXPECTED_MOST_USED_WORDS);
        mockMvc.perform(get(REQUEST).param("numberOfWords", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0]", Matchers.is("review")))
                .andExpect(jsonPath("$[1]", Matchers.is("text")));
    }
}
