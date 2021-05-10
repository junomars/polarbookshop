package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.Book
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import java.time.Year

@JsonTest
internal class BookJsonTests {
    @Autowired
    lateinit var json: JacksonTester<Book>

    @Test
    @Throws(Exception::class)
    fun testSerialize() {
        val book = Book("1234567890", "Title", "Author", Year.of(1973), 9.90)
        Assertions.assertThat(json.write(book)).extractingJsonPathStringValue("@.isbn")
            .isEqualTo("1234567890")
        Assertions.assertThat(json.write(book)).extractingJsonPathStringValue("@.title")
            .isEqualTo("Title")
        Assertions.assertThat(json.write(book)).extractingJsonPathStringValue("@.author")
            .isEqualTo("Author")
        Assertions.assertThat(json.write(book)).extractingJsonPathStringValue("@.publishingYear")
            .isEqualTo("1973")
        Assertions.assertThat(json.write(book)).extractingJsonPathNumberValue("@.price")
            .isEqualTo(9.90)
    }

    @Test
    @Throws(Exception::class)
    fun testDeserialize() {
        val content =
            "{\"isbn\":\"1234567890\",\"title\":\"Title\", \"author\":\"Author\", \"publishingYear\":\"1973\", \"price\":9.90}"
        Assertions.assertThat(
            json.parse(content)
        )
            .usingRecursiveComparison().isEqualTo(Book("1234567890", "Title", "Author", Year.of(1973), 9.90))
        Assertions.assertThat(json.parseObject(content).isbn).isEqualTo("1234567890")
    }
}