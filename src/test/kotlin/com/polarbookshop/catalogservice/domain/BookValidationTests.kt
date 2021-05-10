package com.polarbookshop.catalogservice.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.time.Year
import javax.validation.Validation
import javax.validation.Validator

internal class BookValidationTests {
    @Test
    fun whenAllFieldsCorrectThenValidationSucceeds() {
        val book = Book("1234567890", "Title", "Author", Year.of(2000), 9.90)
        val violations = validator.validate(book)
        Assertions.assertThat(violations).isEmpty()
    }

    @Test
    fun whenIsbnDefinedButIncorrectThenValidationFails() {
        val book = Book("a234567890", "Title", "Author", Year.of(2000), 9.90)
        val violations = validator.validate(book)
        Assertions.assertThat(violations).hasSize(1)
        Assertions.assertThat(violations.iterator().next().message)
            .isEqualTo("The ISBN format must follow the standards ISBN-10 or ISBN-13.")
    }

    companion object {
        private lateinit var validator: Validator

        @BeforeAll
        @JvmStatic
        fun setUp() {
            val factory = Validation.buildDefaultValidatorFactory()
            validator = factory.validator
        }
    }
}