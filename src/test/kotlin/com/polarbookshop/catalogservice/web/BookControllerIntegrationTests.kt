package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.Book
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import java.time.Year

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class BookControllerIntegrationTests {
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun whenPostRequestThenBookCreated() {
        val expectedBook = Book("1231231231", "Title", "Author", Year.of(1991), 9.90)
        val response = restTemplate.postForEntity(
            "/books", expectedBook,
            Book::class.java
        )
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        Assertions.assertThat(response.body).isNotNull
        Assertions.assertThat(response.body!!.isbn).isEqualTo(expectedBook.isbn)
    }
}