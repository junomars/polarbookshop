package com.polarbookshop.catalogservice.domain

import java.time.Year
import javax.validation.constraints.NotBlank
import javax.validation.constraints.PastOrPresent
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

data class Book(
    @get:NotBlank(message = "The book ISBN must be defined")
    @get:Pattern(
        regexp = "^(97([89]))?\\d{9}(\\d|X)$",
        message = "The ISBN format must follow the standards ISBN-10 or ISBN-13."
    )
    val isbn: String,
    @get:NotBlank(message = "The book title must be defined.")
    var title: String,
    @get:NotBlank(message = "The book author must be defined.")
    var author: String,
    @get:PastOrPresent(message = "The book cannot have been published in the future.")
    var publishingYear: Year,
    @get:Positive(message = "The book price must be greater than zero.")
    var price: Double
)