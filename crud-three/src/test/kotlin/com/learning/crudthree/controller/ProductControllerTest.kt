package com.learning.crudthree.controller

import org.hamcrest.Matchers.hasKey
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @Test
    fun `should create product and return created response`() {
        val request = """
            {
              "name": "Keyboard",
              "price": 1200.50,
              "imageUrl": "https://example.com/keyboard.png"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isCreated)
            .andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/api/v1/products/")))
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.name").value("Keyboard"))
    }

    @Test
    fun `should return validation error when product payload is invalid`() {
        val request = """
            {
              "name": "",
              "price": 0
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.errors", hasKey("name")))
            .andExpect(jsonPath("$.errors", hasKey("price")))
    }

    @Test
    fun `should return not found when product does not exist`() {
        mockMvc.perform(get("/api/v1/products/999"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.success").value(false))
    }
}
