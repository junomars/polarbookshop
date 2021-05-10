package com.polarbookshop.catalogservice.persistence

import com.polarbookshop.catalogservice.domain.Book
import com.polarbookshop.catalogservice.domain.BookRepository
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class BookRepositoryImpl : BookRepository {
    override fun findAll(): Collection<Book> {
        return books.values
    }

    override fun findByIsbnOrNull(isbn: String): Book? {
        return books[isbn]
    }

    override fun existsByIsbn(isbn: String): Boolean {
        return books[isbn] != null
    }

    override fun save(book: Book): Book {
        books[book.isbn] = book
        return book
    }

    override fun deleteByIsbn(isbn: String) {
        books.remove(isbn)
    }

    companion object {
        private val books: MutableMap<String, Book> = ConcurrentHashMap()
    }
}