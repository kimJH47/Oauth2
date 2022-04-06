package com.oauth2.exam.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/*@SpringBootTest
@AutoConfigureMockMvc*/
@WebMvcTest
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void returnHello() throws Exception {
        String hello = "hello";
        mvc.perform(get("/hello"))
           .andExpect(status().isOk())
           .andExpect(content().string(hello));

    }

    @Test
    public void helloDtoRequestTest() throws Exception {
        String name = "test";
        int amount = 1000;
        mvc.perform(get("/hello/dto")
                   .param("name", name)
                   .param("amount", String.valueOf(amount)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name", is(name)))
           .andExpect(jsonPath("$.amount",is(amount)));
    }

}